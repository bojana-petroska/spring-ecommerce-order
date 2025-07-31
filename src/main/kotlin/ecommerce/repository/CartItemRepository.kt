package ecommerce.repository

import ecommerce.model.CartItem
import ecommerce.model.Member
import ecommerce.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface CartItemRepository : JpaRepository<CartItem, Long> {
    fun findByMemberAndProduct(
        member: Member,
        product: Product,
    ): Optional<CartItem>

    fun findAllByMember(member: Member): List<CartItem>
//    fun addItemToCart(
//        memberId: Long,
//        productId: Long,
//        quantity: Int = 1,
//    ): Long {
//        val existing = isItemExistInCart(memberId, productId)
//        return when (existing) {
//            true -> addItemIfExistInCart(memberId, productId, quantity)
//            false -> addItemIfNotExistInCart(memberId, productId, quantity)
//        }
//    }
//
//    private fun isItemExistInCart(
//        memberId: Long,
//        productId: Long,
//    ): Boolean {
//        val sql = "SELECT COUNT(*) FROM cart_item WHERE member_id = ? AND product_id = ?"
//        val count = db.queryForObject(sql, Long::class.java, memberId, productId)
//        return count != null && count > 0
//    }
//
//    private fun addItemIfExistInCart(
//        memberId: Long,
//        productId: Long,
//        quantity: Int,
//    ): Long {
//        val keyHolder = GeneratedKeyHolder()
//        db.update(
//            { connection ->
//                connection.prepareStatement(
//                    "UPDATE cart_item SET quantity = quantity + ? WHERE member_id = ? AND product_id = ?",
//                    arrayOf("id"),
//                ).apply {
//                    setInt(1, quantity)
//                    setLong(2, memberId)
//                    setLong(3, productId)
//                }
//            },
//            keyHolder,
//        )
//        return keyHolder.key?.toLong() ?: throw IllegalStateException(MESSAGE_INSERT_RETRIEVE_ID_FAILED)
//    }
//
//    private fun addItemIfNotExistInCart(
//        memberId: Long,
//        productId: Long,
//        quantity: Int,
//    ): Long {
//        val keyHolder = GeneratedKeyHolder()
//        db.update(
//            { connection ->
//                connection.prepareStatement(
//                    "INSERT INTO cart_item (member_id, product_id, quantity) VALUES (?, ?, ?)",
//                    arrayOf("id"),
//                ).apply {
//                    setLong(1, memberId)
//                    setLong(2, productId)
//                    setInt(3, quantity)
//                }
//            },
//            keyHolder,
//        )
//        db.update(
//            "INSERT INTO cart_item_event (member_id, product_id) VALUES (?, ?)",
//            memberId,
//            productId,
//        )
//        return keyHolder.key?.toLong() ?: throw IllegalStateException(MESSAGE_INSERT_RETRIEVE_ID_FAILED)
//    }
//
//    fun getCartItemsByMemberId(memberId: Long): List<CartItem>
//
//    fun removeItemFromCart(
//        memberId: Long,
//        productId: Long,
//    ): Int
//
//    fun updateItemQuantityInCart(
//        memberId: Long,
//        productId: Long,
//        quantity: Int,
//    ): Int

//    fun getTop5AddedProductsInLast30Days(): List<TopProductStats>
//
//    fun getActiveMembersInLast7Days(): List<ActiveMemberInfo>

    companion object {
        const val MESSAGE_INSERT_RETRIEVE_ID_FAILED = "insert - Failed to retrieve ID"
    }
}
