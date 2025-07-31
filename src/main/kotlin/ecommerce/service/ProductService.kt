package ecommerce.service

import ecommerce.dto.ProductForm
import ecommerce.exception.InternalServerErrorException
import ecommerce.exception.NotFoundException
import ecommerce.exception.ProductNameAlreadyExistsException
import ecommerce.model.Product
import ecommerce.repository.ProductRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductRepository) {
    fun insert(form: ProductForm): Product {
        checkProductNameExists(form.name)
        val product = ProductForm.toProduct(form)
        val savedProduct = productRepository.save(product)
        return productRepository.findByIdOrNull(savedProduct.id)
            ?: throw InternalServerErrorException("ProductService.insert() - Product with ID ${savedProduct.id} not found")
    }

    fun findAll(): List<Product> = productRepository.findAll()

    fun findById(id: Long): Product = productRepository.findByIdOrNull(id) ?: throw NotFoundException(MESSAGE_PRODUCT_NOT_FOUND)

    fun update(
        form: ProductForm,
        id: Long,
    ): Product {
        val originalProduct =
            productRepository.findByIdOrNull(id)
                ?: throw InternalServerErrorException("ProductService.update() - Product with ID $id not found")
        val originalName = originalProduct.name
        checkProductNameExists(form.name, originalName)
        val product = ProductForm.toEntity(form, id)
        product.changeName(form.name)
        product.changePrice(form.price)
        product.changeImageUrl(form.imageUrl)
        productRepository.save(product)
        return productRepository.findByIdOrNull(id)
            ?: throw InternalServerErrorException(MESSAGE_PRODUCT_NOT_FOUND)
    }

    fun delete(id: Long) {
        val product = productRepository.findByIdOrNull(id) ?: throw NotFoundException("Product not found - ID: $id")
        productRepository.delete(product)
    }

    private fun checkProductNameExists(
        name: String,
        originalName: String? = null,
    ) {
        if (originalName != null && name == originalName) {
            return
        } else if (productRepository.findByName(name).isPresent) {
            val message = "Product with name '$name' already exists."
            throw ProductNameAlreadyExistsException(message)
        }
    }

    companion object {
        const val MESSAGE_PRODUCT_NOT_FOUND = "Product not found"
    }
}
