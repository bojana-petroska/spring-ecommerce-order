package ecommerce.dto.order

data class OrderRequest(
    val productId: Long,
    val optionId: Long,
    val quantity: Int,
    val paymentMethod: String,
    val currency: String = "usd",
)
