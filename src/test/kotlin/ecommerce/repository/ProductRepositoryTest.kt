package ecommerce.repository

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import practice.Product

@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    private lateinit var products: ProductRepository

    @Test
    fun save() {
        val expected = Product(name = "abc", price = 1.2, imageUrl = "https://sample.com/2")
        val actual = products.save(expected)
        Assertions.assertThat(actual.id).isNotZero()
        Assertions.assertThat(actual.name).isEqualTo(expected.name)
    }

    @Test
    fun findById() {
        val product = Product(name = "abcd", price = 1.2, imageUrl = "https://sample.com/2")
        val returned = products.save(product)
        val actual = products.findById(returned.id).get()
        Assertions.assertThat(actual.id).isNotZero()
        Assertions.assertThat(actual.id).isEqualTo(returned.id)
        Assertions.assertThat(actual.name).isEqualTo(product.name)
    }

    @Test
    fun findAll() {
        val actual = products.findAll()
        Assertions.assertThat(actual).isEmpty()
    }

    @Test
    fun `findAll - has some data`() {
        save()
        findById()
        val actual = products.findAll()
        Assertions.assertThat(actual).isNotEmpty()
        Assertions.assertThat(actual).hasSize(2)
    }
}
