package ecommerce.controller.api

import ecommerce.dto.CartAddItemForm
import ecommerce.dto.CartUpdateQuantityForm
import ecommerce.model.CartItem
import ecommerce.model.Member
import ecommerce.service.CartItemService
import ecommerce.ui.LoginMember
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/cart")
class CartController(
    private val cartItemService: CartItemService,
) {
    @GetMapping
    fun viewCart(
        @LoginMember member: Member,
    ): ResponseEntity<List<CartItem>> {
        val memberId = member.id
        val cartItems = cartItemService.getCartItemsByMemberId(memberId)
        return ResponseEntity.ok(cartItems)
    }

    @PostMapping
    fun addToCart(
        @RequestBody @Valid cartForm: CartAddItemForm,
        @LoginMember member: Member,
    ): ResponseEntity<String> {
        val memberId = member.id
        val cartItem = cartItemService.addCartItem(memberId, cartForm.productId, cartForm.quantity)
        return ResponseEntity.ok(MESSAGE_ADD_SUCCESS)
    }

    @PutMapping("/{productId}")
    fun updateQuantity(
        @PathVariable productId: Long,
        @RequestBody @Valid cartForm: CartUpdateQuantityForm,
        @LoginMember member: Member,
    ): ResponseEntity<String> {
        val memberId = member.id
        val message = cartItemService.updateQuantity(memberId, productId, cartForm.quantity)
        return ResponseEntity.ok(message)
    }

    @DeleteMapping("/{productId}")
    fun removeFromCart(
        @PathVariable productId: Long,
        @LoginMember member: Member,
    ): ResponseEntity<String> {
        val memberId = member.id
        val message = cartItemService.removeCartItem(memberId, productId)
        return ResponseEntity.ok(message)
    }

    companion object {
        const val MESSAGE_ADD_SUCCESS = "Item added to cart"
    }
}
