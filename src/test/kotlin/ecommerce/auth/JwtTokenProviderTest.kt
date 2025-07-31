package ecommerce.auth

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JwtTokenProviderTest {
    @Autowired private lateinit var jwtTokenProvider: JwtTokenProvider

    @LocalServerPort
    private var port: Int = 0

    @Test
    fun `create and validate token successfully`() {
        val email = "test@email.com"
        val token = jwtTokenProvider.createToken(email)

        assertThat(token).isNotNull()
        assertThat(token).isNotEmpty()
        assertThat(jwtTokenProvider.validateToken(token)).isTrue()
        assertThat(jwtTokenProvider.getPayload(token)).isEqualTo(email)
    }

    @Test
    fun `validateToken() - return false when validate invalid token`() {
        assertThat(jwtTokenProvider.validateToken("some.invalid.token")).isFalse()
    }
}
