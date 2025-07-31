package ecommerce.service

import ecommerce.auth.JwtTokenProvider
import ecommerce.dto.LoginForm
import ecommerce.dto.RegisterForm
import ecommerce.exception.AuthorizationException
import ecommerce.exception.MemberEmailAlreadyExistsException
import ecommerce.model.Member
import ecommerce.repository.CartRepository
import ecommerce.repository.MemberRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Transactional
class AuthServiceTest(
    @Autowired private val jwtTokenProvider: JwtTokenProvider,
    @Autowired private val authService: AuthService,
    @Autowired private val memberRepository: MemberRepository,
    @Autowired private val cartRepository: CartRepository,
) {
    fun register(email: String): Member {
        val password = "test1234"
        val registerForm = RegisterForm(email, password)
        return authService.registerMember(registerForm)
    }

    @BeforeEach
    fun setup() {
        memberRepository.deleteAll()
        cartRepository.deleteAll()
    }

    @Test
    fun `registerMember() - should throw an exception when email already exists`() {
        val email = "dan@htc.com"
        val password = "test1234"
        register(email)
        val registerForm = RegisterForm(email, password)
        assertThrows<MemberEmailAlreadyExistsException> { authService.registerMember(registerForm) }
    }

    @Test
    fun `registerMember() - should return a member with id when registration information is valid`() {
        val email = "test@email.com"
        val password = "test1234"
        val registerForm = RegisterForm(email, password)
        val registeredMember = authService.registerMember(registerForm)
        assertThat(registeredMember.id).isNotNull()
        assertThat(registeredMember.email).isEqualTo(email)
        assertThat(registeredMember.password).isEqualTo(password)
    }

    @Test
    fun `loginMember() - should throw an exception when email does not exists`() {
        val email = "test@email.com"
        val password = "test1234"
        val loginForm = LoginForm(email, password)
        assertThrows<AuthorizationException> { authService.loginMember(loginForm) }
    }

    @Test
    fun `loginMember() - should throw an exception when password is not valid`() {
        val email = "dan@htc.com"
        val password = "test1234"
        val registerForm = RegisterForm(email, password)
        authService.registerMember(registerForm)

        val loginForm = LoginForm(email, password + "123")
        assertThrows<AuthorizationException> { authService.loginMember(loginForm) }
    }

    @Test
    fun `loginMember() - should return an AuthResponse with access token when login credentials are valid`() {
        val email = "dan@htc.com"
        val password = "test1234"
        register(email)
        val loginForm = LoginForm(email, password)
        val authResponse = authService.loginMember(loginForm)
        assertThat(authResponse.accessToken).isNotNull()
    }

    @Test
    fun `findMemberByToken() - should return a member with id when token is valid`() {
        register(EMAIL)
        val token = jwtTokenProvider.createToken(EMAIL)
        val member = authService.findMemberByToken(token)
        assertThat(member).isInstanceOf(Member::class.java)
        assertThat(member.id).isNotNull()
    }

    companion object {
        private const val EMAIL = "san@htc.com"
    }
}
