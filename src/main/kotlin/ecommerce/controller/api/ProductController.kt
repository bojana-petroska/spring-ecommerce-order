package ecommerce.controller.api

import ecommerce.dto.ProductForm
import ecommerce.exception.ProductNameAlreadyExistsException
import ecommerce.model.Product
import ecommerce.service.ProductService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/products")
class ProductController(private val productService: ProductService) {
    @PostMapping
    fun createProduct(
        @RequestBody @Valid productForm: ProductForm,
    ): ResponseEntity<Product> {
        val product = productService.insert(productForm)
        val uri = URI.create("/api/products/${product.id}")
        return ResponseEntity.created(uri).body(product)
    }

    @GetMapping
    fun getProducts(
        @RequestParam(defaultValue = "0") pageNumber: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
        @RequestParam(defaultValue = "name") sortBy: String,
    ): ResponseEntity<Page<Product>> {
        val productPage: Page<Product> =
            when (sortBy.isEmpty()) {
                true -> productService.getPaginatedProducts(pageNumber, pageSize)
                false -> productService.getPaginatedProducts(pageNumber, pageSize, sortBy)
            }
        return ResponseEntity.ok(productPage)
    }

    @GetMapping("{id}")
    fun getProduct(
        @PathVariable id: Long,
    ): ResponseEntity<Product> {
        val product = productService.findById(id)
        return ResponseEntity.ok(product)
    }

    @PutMapping("{id}")
    fun updateProduct(
        @PathVariable id: Long,
        @RequestBody @Valid productForm: ProductForm,
    ): ResponseEntity<Product> {
        val product = productService.update(productForm, id)
        return ResponseEntity.ok(product)
    }

    @DeleteMapping("{id}")
    fun deleteProduct(
        @PathVariable id: Long,
    ): ResponseEntity<Void> {
        productService.delete(id)
        return ResponseEntity.noContent().build()
    }

    @ExceptionHandler(ProductNameAlreadyExistsException::class)
    fun handleProductNameAlreadyExistsExceptionHandler(e: Exception): ResponseEntity<Map<String, Any>> {
        val error = mapOf("name" to e.message)
        val errorBody = mapOf("errors" to error)
        println("ProductNameAlreadyExistsException occurred: $errorBody")
        return ResponseEntity.badRequest().body(errorBody)
    }
}
