package ecommerce.repository

import ecommerce.model.Product
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class ProductRepositoryTest(
    @Autowired private val products: ProductRepository,
    @Autowired private val testEntityManager: TestEntityManager,
) {
    @AfterEach
    fun clean() {
        testEntityManager.clear()
    }

    @Test
    fun save() {
        val expected = Product(name = "abc", price = 1.2, imageUrl = "https://sample.com/2")
        val actual = products.save(expected)
        assertThat(actual.id).isNotZero()
        assertThat(actual.name).isEqualTo(expected.name)
    }

    @Test
    fun findById() {
        val product = Product(name = "abcd", price = 1.2, imageUrl = "https://sample.com/2")
        val returned = products.save(product)
        val actual = products.findById(returned.id).get()
        assertThat(actual.id).isNotZero()
        assertThat(actual.id).isEqualTo(returned.id)
        assertThat(actual.name).isEqualTo(product.name)
    }

    @Test
    fun findByName() {
        val product = Product(name = "abcd", price = 1.2, imageUrl = "https://sample.com/2")
        val returned = products.save(product)
        val actual = products.findByName("abcd").get()
        assertThat(actual.id).isNotZero()
        assertThat(actual.id).isEqualTo(returned.id)
        assertThat(actual.name).isEqualTo(product.name)
    }

    @Test
    fun `findAll - has some data`() {
        save()
        findById()
        val actual = products.findAll()
        assertThat(actual).isNotEmpty()
        assertThat(actual).hasSize(9)
    }

    @Test
    fun update() {
        val newProduct = Product(name = "Iron body", price = 99.0, imageUrl = "https://alexnsan.comics/imageurl/123")
        val product = products.save(newProduct)

        val expectedName = "abc"
        val expectedPrice = 1.2
        val expectedImage = "https://sample.com/2"

        product.changeName(expectedName)
        product.changePrice(expectedPrice)
        product.changeImageUrl(expectedImage)

        val target = products.findById(product.id).get()

        assertThat(target.id).isEqualTo(product.id)
        assertThat(target.name).isEqualTo(product.name)
        assertThat(target.price).isEqualTo(product.price)
        assertThat(target.imageUrl).isEqualTo(product.imageUrl)
    }

    @Test
    fun delete() {
        val newProduct = Product(name = "Iron body", price = 99.0, imageUrl = "https://alexnsan.comics/imageurl/123")
        val product = products.save(newProduct)

        products.delete(product)

        val actual = products.findByIdOrNull(product.id)
        assertThat(actual).isNull()
    }

    @Test
    fun `findByName() - return true if a product with same name exists`() {
        val target = products.findByName("Iron Man")
        assertThat(target).isNotNull()
    }

    @Test
    fun `findByName() - throws an exception if a product with same name does not exist`() {
        assertThrows<NoSuchElementException> { products.findByName("Iron Body").get() }
    }
}
