package ecommerce.service

import ecommerce.dto.order.OrderRequest
import ecommerce.dto.order.OrderResponse
import ecommerce.dto.payment.PaymentRequest
import ecommerce.exception.NotFoundException
import ecommerce.exception.StripeClientException
import ecommerce.model.Member
import ecommerce.model.Order
import ecommerce.model.OrderItem
import ecommerce.model.OrderStatus
import ecommerce.model.Payment
import ecommerce.repository.CartItemRepository
import ecommerce.repository.OptionRepository
import ecommerce.repository.OrderRepository
import ecommerce.repository.ProductRepository
import ecommerce.stripe.StripeClient
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class OrderService(
    private val stripeClient: StripeClient,
    private val productRepository: ProductRepository,
    private val optionRepository: OptionRepository,
    private val orderRepository: OrderRepository,
    private val cartItemRepository: CartItemRepository,
) {
    @Transactional
    fun placeOrder(
        member: Member,
        req: OrderRequest,
    ): OrderResponse {
        val product =
            productRepository.findByIdOrNull(req.productId)
                ?: throw NotFoundException("Product not found")

        val option =
            optionRepository.findByIdOrNull(req.optionId)
                ?: throw NotFoundException("Option not found")

        if (option.product?.id != product.id) {
            throw IllegalArgumentException("Selected option does not belong to the specified product.")
        }

        if (option.quantity < req.quantity) {
            throw IllegalArgumentException("Not enough stock available")
        }

        val totalAmount = calculateAmount(product.price.toInt(), req.quantity)
        val paymentRequest =
            PaymentRequest(
                amount = totalAmount,
                currency = req.currency,
                paymentMethod = req.paymentMethod,
            )

        val paymentResponse = stripeClient.createCheckoutSession(paymentRequest)

        if (paymentResponse == null || paymentResponse.id.isBlank()) {
            throw StripeClientException.from(paymentResponse?.errorMessage ?: "Stripe did not return a valid response.")
        }

        option.quantity -= req.quantity
        optionRepository.save(option)

        val existingCartItem = cartItemRepository.findByMemberAndProduct(member, product)
        if (existingCartItem.isPresent) {
            cartItemRepository.delete(existingCartItem.get())
        }

        val order =
            Order(
                member = member,
                status = OrderStatus.PAID,
                createdAt = LocalDateTime.now(),
            )

        val orderItem =
            OrderItem(
                product = product,
                option = option,
                quantity = req.quantity,
            )
        order.addItem(orderItem)

        val payment =
            Payment(
                order = order,
                paymentIntentId = paymentResponse.id,
                amount = totalAmount,
                currency = req.currency,
            )
        order.assignPayment(payment)

        orderRepository.save(order)

        return OrderResponse(
            success = true,
            message = "Order placed successfully",
            paymentIntentId = paymentResponse.id,
        )
    }

    @Transactional
    fun getOrdersForMember(member: Member): List<OrderResponse> {
        val orders = orderRepository.findAllByMember(member)
        return orders.map {
            OrderResponse(
                success = true,
                message = "Order with ${it.items.size} item(s)",
                paymentIntentId = it.payment?.paymentIntentId ?: "N/A",
            )
        }
    }

    private fun calculateAmount(
        price: Int,
        quantity: Int,
    ): Int {
        return price * quantity
    }
}
