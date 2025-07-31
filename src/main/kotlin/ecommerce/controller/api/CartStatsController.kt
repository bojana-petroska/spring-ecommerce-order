package ecommerce.controller.api

import ecommerce.repository.CartItemRepository
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/admin/cart-stats")
class CartStatsController(
    private val cartItemRepository: CartItemRepository,
) {
//    @GetMapping("/top5-products")
//    fun getTop5Products(
//        @LoginMember member: Member,
//    ): ResponseEntity<List<TopProductStats>> {
//        member.id ?: throw InternalServerErrorException(MESSAGE_AUTH_FAILED)
//        val stats = cartItemRepository.getTop5AddedProductsInLast30Days()
//        return ResponseEntity.ok(stats)
//    }

//    @GetMapping("/active-members")
//    fun getActiveMembers(
//        @LoginMember member: Member,
//    ): ResponseEntity<List<ActiveMemberInfo>> {
//        member.id ?: throw InternalServerErrorException(MESSAGE_AUTH_FAILED)
//        val members = cartItemRepository.getActiveMembersInLast7Days()
//        return ResponseEntity.ok(members)
//    }

    companion object {
        const val MESSAGE_AUTH_FAILED = "auth failed"
    }
}
