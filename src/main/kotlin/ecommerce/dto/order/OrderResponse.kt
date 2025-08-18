package ecommerce.dto.order

data class OrderResponse(
    val success: Boolean,
    val message: String,
    val paymentIntentId: String? = null,
)
