package ecommerce.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "members")
class Member(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Column(nullable = false, unique = true)
    var email: String,
    @Column(nullable = false)
    var password: String,
    @Column(nullable = true)
    var role: String? = null,
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    var cart: Cart,
    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "cart_item_id", referencedColumnName = "id")
    var cartItems: List<CartItem> = emptyList(),
) {
//    companion object {
//        fun toEntity(
//            member: Member,
//            id: Long,
//        ): Member {
//            return Member(id, member.email, member.password)
//        }
//
//        fun from(loginForm: LoginForm): Member {
//            return Member(email = loginForm.email, password = loginForm.password)
//        }
//
//        fun from(registerForm: RegisterForm): Member {
//            return Member(email = registerForm.email, password = registerForm.password)
//        }
//
//        fun toResponse(entity: Member): MemberResponse {
//            val id = entity.id ?: throw InternalServerErrorException("Member ID is null")
//            return MemberResponse(id = id, email = entity.email)
//        }
//    }
}
