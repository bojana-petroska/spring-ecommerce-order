package ecommerce.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class CartItemRepositoryTest(
    @Autowired private val cartItemRepository: CartItemRepository,
) {
//    @Test
//    fun getTop5AddedProductsInLast30Days() {
//        val stats = cartItemRepository.getTop5AddedProductsInLast30Days()
//        assertThat(stats).hasSize(5)
//    }
//
//    @Test
//    fun getActiveMembersInLast7Days() {
//        val stats = cartItemRepository.getActiveMembersInLast7Days()
//        assertThat(stats).hasSize(3)
//    }

    companion object {
        const val MEMBER_ID = 1L
        const val PRODUCT_ID = 1L
        const val QUANTITY = 10
    }
}
