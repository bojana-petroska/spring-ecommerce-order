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

    companion object {
        const val MESSAGE_INSERT_RETRIEVE_ID_FAILED = "insert - Failed to retrieve ID"
    }
}
