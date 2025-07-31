package ecommerce.service

import ecommerce.repository.CartItemRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Transactional
class CartStatisticsServiceTest(
    @Autowired private val cartItemRepository: CartItemRepository,
    @Autowired private val cartStatisticsService: CartStatisticsService,
) {
    @Test
    fun getTop5AddedProductsInLast30Days() {
        val actual = cartStatisticsService.getTop5AddedProductsInLast30Days()
        actual.size
        assertThat(actual).hasSize(5)
    }

    @Test
    fun getActiveMembersInLast7Days() {
        val actual = cartStatisticsService.getActiveMembersInLast7Days()
        actual.size
        assertThat(actual).hasSize(3)
        assertThat(actual[0].email).isEqualTo("san@htc.com")
        assertThat(actual[1].email).isEqualTo("dan@htc.com")
        assertThat(actual[2].email).isEqualTo("ann@htc.com")
        assertThat(actual[2].email).isNotEqualTo("min@htc.com")
    }
}
