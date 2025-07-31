package ecommerce.controller

import ecommerce.controller.api.ProductController
import ecommerce.dto.ProductForm
import ecommerce.exception.NotFoundException
import ecommerce.model.Product
import ecommerce.repository.ProductRepository
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.transaction.annotation.Transactional

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Transactional
class ProductControllerTest(
    @Autowired private val productRepository: ProductRepository,
    @Autowired private val controller: ProductController,
) {
    fun create(productName: String = "product1"): Product {
        val product = Product(name = productName, price = 1.5, imageUrl = "https://www.product.com/image/1")
        return productRepository.save(product)
    }

    @Test
    fun `create() - should insert product and return 201 when form is valid`() {
        RestAssured
            .given().log().all()
            .body(Product(name = "product1 [new]", price = 1.5, imageUrl = "https://www.product.com/image/1"))
            .contentType(ContentType.JSON)
            .`when`().post("/api/products")
            .then().log().all()
            .assertThat()
            .statusCode(HttpStatus.CREATED.value())
    }

    @Test
    fun `create() - should return 400 when name is blank`() {
        val response =
            RestAssured
                .given().log().all()
                .body(Product(name = "", price = 1.5, imageUrl = "https://www.product.com/image/1"))
                .contentType(ContentType.JSON)
                .`when`().post("/api/products")
                .then().log().all().extract()

        val targets =
            listOf(
                "Contains unallowed character",
                "Product name is required",
                "Must be no more than 15 characters, including spaces",
            )
        val resBody = response.jsonPath().getMap<String, String>("errors")
        val actual = resBody["name"]
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value())
        assertThat(actual).isIn(targets)
    }

    @Test
    fun `create() - should return 400 when name is more than 15 characters`() {
        val expected = "Must be no more than 15 characters, including spaces"
        RestAssured
            .given().log().all()
            .body(
                Product(
                    name = "this is very long name",
                    price = 1.5,
                    imageUrl = "https://www.product.com/image/1",
                ),
            )
            .contentType(ContentType.JSON)
            .`when`().post("/api/products")
            .then().log().all()
            .assertThat()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("errors.name", equalTo(expected))
    }

    @Test
    fun `create() - should return 400 when name has unallowed character`() {
        val expected = "Contains unallowed character"
        RestAssured
            .given().log().all()
            .body(Product(name = "I am product!", price = 1.5, imageUrl = "https://www.product.com/image/1"))
            .contentType(ContentType.JSON)
            .`when`().post("/api/products")
            .then().log().all()
            .assertThat()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("errors.name", equalTo(expected))
    }

    @Test
    fun `create() - should return 400 when price is 0`() {
        val expected = "Product price must be greater than zero"
        RestAssured
            .given().log().all()
            .body(Product(name = "base", price = 0.0, imageUrl = "https://www.product.com/image/1"))
            .contentType(ContentType.JSON)
            .`when`().post("/api/products")
            .then().log().all()
            .assertThat()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("errors.price", equalTo(expected))
    }

    @Test
    fun `create() - should return 400 when image URL is blank`() {
        val response =
            RestAssured
                .given().log().all()
                .body(Product(name = "base", price = 2.0, imageUrl = ""))
                .contentType(ContentType.JSON)
                .`when`().post("/api/products")
                .then().log().all().extract()

        val expected =
            listOf(
                "Product image URL is required",
                "Must start with 'https://'.",
            )
        val resBody = response.jsonPath().getMap<String, String>("errors")
        val actual = resBody["imageUrl"]
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value())
        assertThat(actual).isIn(expected)
    }

    @Test
    fun `create() - should return 400 when image URL is not valid`() {
        val expected = "Must start with 'https://'."
        RestAssured
            .given().log().all()
            .body(Product(name = "base", price = 2.0, imageUrl = "ssh://www.product.com/image/1"))
            .contentType(ContentType.JSON)
            .`when`().post("/api/products")
            .then().log().all()
            .assertThat()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("errors.imageUrl", equalTo(expected))
    }

    @Test
    fun `create() - should return 400 when name of product already exists`() {
        val name = "Iron Man"
        val expected = "Product with name '$name' already exists."
        RestAssured
            .given().log().all()
            .body(Product(name = name, price = 1.5, imageUrl = "https://www.product.com/image/1"))
            .contentType(ContentType.JSON)
            .`when`().post("/api/products")
            .then().log().all()
            .assertThat()
            .statusCode(HttpStatus.CREATED.value())

        RestAssured
            .given().log().all()
            .body(Product(name = name, price = 2.0, imageUrl = "https://www.product.com/image/1"))
            .contentType(ContentType.JSON)
            .`when`().post("/api/products")
            .then().log().all()
            .assertThat()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("errors.name", equalTo(expected))
    }

    @Test
    fun readProducts() {
        productRepository.deleteAll()
        create()
        create("abc")
        val response = controller.getProducts()
        assertThat(response.body?.size).isEqualTo(2)
        assertThat(response.statusCode.value()).isEqualTo(HttpStatus.OK.value())
    }

    @Test
    fun readProduct() {
        create()
        val product = create("abc")
        val response = controller.getProduct(product.id)
        assertThat(response.statusCode.value()).isEqualTo(HttpStatus.OK.value())
    }

    @Test
    fun `readProduct() - but product doesn't exist`() {
        val id = 100
        RestAssured
            .given().log().all()
            .contentType(ContentType.JSON)
            .`when`().get("/api/products/$id")
            .then().log().all()
            .assertThat()
            .statusCode(HttpStatus.NOT_FOUND.value())
    }

    @Test
    fun `readProduct() - unit test, throw exception if product doesn't exist, `() {
        assertThrows<NotFoundException> { controller.getProduct(10) }
    }

    @Test
    fun update() {
        val product = create()
        val newProductForm =
            ProductForm(name = "new product", price = 1.6, imageUrl = "https://www.product.com/image/2")
        val response = controller.updateProduct(product.id, newProductForm)
        assertThat(response.statusCode.value()).isEqualTo(HttpStatus.OK.value())
        val actual = response.body
        assertThat(actual?.id).isEqualTo(product.id)
        assertThat(actual?.name).isEqualTo(newProductForm.name)
        assertThat(actual?.price).isEqualTo(newProductForm.price)
        assertThat(actual?.imageUrl).isEqualTo(newProductForm.imageUrl)
    }

    @Test
    fun `update() - should return 400 when name is blank`() {
        val targetId = 1L
        val name = ""
        val response =
            RestAssured
                .given().log().all()
                .body(Product(id = targetId, name = name, price = 1.5, imageUrl = "https://www.product.com/image/1"))
                .contentType(ContentType.JSON)
                .`when`().put("/api/products/$targetId")
                .then().log().all().extract()

        val targets =
            listOf(
                "Contains unallowed character",
                "Product name is required",
                "Must be no more than 15 characters, including spaces",
            )
        val resBody = response.jsonPath().getMap<String, String>("errors")
        val actual = resBody["name"]
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value())
        assertThat(actual).isIn(targets)
    }

    @Test
    fun `update() - should return 400 when price is 0`() {
        val targetId = 1L
        val expected = "Product price must be greater than zero"
        RestAssured
            .given().log().all()
            .body(Product(id = targetId, name = "base", price = 0.0, imageUrl = "https://www.product.com/image/1"))
            .contentType(ContentType.JSON)
            .`when`().put("/api/products/$targetId")
            .then().log().all()
            .assertThat()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("errors.price", equalTo(expected))
    }

    @Test
    fun `update() - should return 400 when image URL is not valid`() {
        val targetId = 1L
        val expected = "Must start with 'https://'."
        RestAssured
            .given().log().all()
            .body(Product(name = "base", price = 2.0, imageUrl = "ssh://www.product.com/image/1"))
            .contentType(ContentType.JSON)
            .`when`().put("/api/products/$targetId")
            .then().log().all()
            .assertThat()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("errors.imageUrl", equalTo(expected))
    }

    @Test
    fun `update() - should return 400 when name of product already exists`() {
        val name = "Super man"
        val name2 = "Ultra man"
        val expected = "Product with name '$name' already exists."

        RestAssured
            .given().log().all()
            .body(Product(name = name, price = 1.5, imageUrl = "https://www.product.com/image/1"))
            .contentType(ContentType.JSON)
            .`when`().post("/api/products")
            .then().log().all()
            .assertThat()
            .statusCode(HttpStatus.CREATED.value())

        val product =
            RestAssured
                .given().log().all()
                .body(Product(name = name2, price = 1.5, imageUrl = "https://www.product.com/image/1"))
                .contentType(ContentType.JSON)
                .`when`().post("/api/products")
                .then().log().all()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()

        val targetId = product.body().jsonPath().getLong("id")

        RestAssured
            .given().log().all()
            .body(Product(id = targetId, name = name, price = 2.0, imageUrl = "https://www.product.com/image/1"))
            .contentType(ContentType.JSON)
            .`when`().put("/api/products/$targetId")
            .then().log().all()
            .assertThat()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("errors.name", equalTo(expected))
    }

    @Test
    fun delete() {
        val product = create()
        val response = controller.deleteProduct(product.id)
        assertThat(response.statusCode.value()).isEqualTo(HttpStatus.NO_CONTENT.value())
    }

    @Test
    fun `delete() - should return 404 when the product to delete doesn't exist`() {
        val id = 100
        RestAssured
            .given().log().all()
            .contentType(ContentType.JSON)
            .`when`().delete("/api/products/$id")
            .then().log().all()
            .assertThat()
            .statusCode(HttpStatus.NOT_FOUND.value())
    }

    @Test
    fun `delete() - unit test, should return 404 when the product to delete doesn't exist`() {
        create()
        assertThrows<NotFoundException> { controller.deleteProduct(1000) }
    }
}
