package ecommerce.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "products")
class Product(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Column(nullable = false, unique = true, length = 15)
    var name: String,
    @Column(nullable = false)
    var price: Double,
    @Column(nullable = false)
    var imageUrl: String,
    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "cart_item_id", referencedColumnName = "id")
    var cartItems: List<CartItem> = emptyList(),
) {
//    companion object {
//        fun toEntity(
//            product: Product,
//            id: Long,
//        ): Product {
//            return Product(id, product.name, product.price, product.imageUrl)
//        }
//    }
}
