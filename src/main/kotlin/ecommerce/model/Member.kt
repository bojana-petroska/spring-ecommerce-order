package ecommerce.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "members")
class Member(
    @Column(nullable = false, unique = true)
    val email: String,
    @Column(nullable = false)
    val password: String,
    val role: String? = null,
    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn
    var cart: Cart = Cart(),
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
)
