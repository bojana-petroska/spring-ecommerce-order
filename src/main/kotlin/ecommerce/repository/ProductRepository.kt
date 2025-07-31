package ecommerce.repository

import ecommerce.model.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface ProductRepository : JpaRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {
    fun findByName(name: String): Optional<Product>

    fun findAllByPrice(
        price: Double,
        pageable: Pageable,
    ): Page<Product>
}
