package ecommerce.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ProductTest {
    @Test
    fun `should create a new product`() {
        val options = listOf(Option(name = "size", quantity = 10), Option(name = "color", quantity = 5))
        val product = Product("lotion", 20.0, "https://lotion.jpeg", options)
        assertThat(product).isNotNull()
        assertThat(product.name).isEqualTo("lotion")
        assertThat(product.price).isEqualTo(20.0)
        assertThat(product.imageUrl).isEqualTo("https://lotion.jpeg")
        assertThat(product.options).hasSize(2)
    }

    @Test
    fun `should throw error when options is empty`() {
        assertThrows<IllegalArgumentException> {
            Product("lotion", 20.0, "https://lotion.jpeg", emptyList())
        }
    }

    @Test
    fun `should throw error when options names are not distinct`() {
        assertThrows<IllegalArgumentException> {
            Product(
                "lotion",
                20.0,
                "https://lotion.jpeg",
                listOf(Option(name = "size", quantity = 10), Option(name = "size", quantity = 5)),
            )
        }
    }

    @Test
    fun `should change name of product`() {
        val product =
            Product("lotion", 20.0, "https://lotion.jpeg", options = listOf(Option(name = "size", quantity = 10)))
        product.changeName("spf")
        assertThat(product.name).isEqualTo("spf")
    }

    @Test
    fun `should change price of product`() {
        val product =
            Product("lotion", 20.0, "https://lotion.jpeg", options = listOf(Option(name = "size", quantity = 10)))
        product.changePrice(25.0)
        assertThat(product.price).isEqualTo(25.0)
    }

    @Test
    fun `should change image of product`() {
        val product =
            Product("lotion", 20.0, "https://lotion.jpeg", options = listOf(Option(name = "size", quantity = 10)))
        product.changeImageUrl("https://lotion2.jpeg")
        assertThat(product.imageUrl).isEqualTo("https://lotion2.jpeg")
    }

    @Test
    fun `should set product reference on new options`() {
        val options = listOf(Option(name = "size", quantity = 10))
        val product = Product("lotion", 20.0, "https://lotion.jpeg", options)
        assertThat(product.options[0].product).isEqualTo(product)
    }

    @Test
    fun `should add new option to product`() {
        val product =
            Product("lotion", 20.0, "https://lotion.jpeg", options = listOf(Option(name = "size", quantity = 10)))
        val newOption = Option(name = "color", quantity = 5)
        product.addOption(newOption)
        assertThat(product.options).hasSize(2)
        assertThat(newOption.product).isEqualTo(product)
    }

    @Test
    fun `should throw error when adding duplicate option name`() {
        val product =
            Product("lotion", 20.0, "https://lotion.jpeg", options = listOf(Option(name = "size", quantity = 10)))
        val newOption = Option(name = "size", quantity = 5)
        assertThrows<IllegalArgumentException> {
            product.addOption(newOption)
        }
    }
}
