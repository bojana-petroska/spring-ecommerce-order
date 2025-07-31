package ecommerce.repository

import ecommerce.model.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface MemberRepository : JpaRepository<Member, Long> {
    fun findByEmail(email: String): Optional<Member>

    companion object {
        const val MESSAGE_INSERT_RETRIEVE_ID_FAILED = "insert - Failed to retrieve ID"
    }
}
