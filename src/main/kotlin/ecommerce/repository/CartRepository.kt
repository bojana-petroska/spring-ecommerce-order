package ecommerce.repository

import ecommerce.model.Cart
import ecommerce.model.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CartRepository : JpaRepository<Cart, Long> {
    fun findByMember(member: Member): Cart
}
