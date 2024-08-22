package jjfactory.diary.application.auth

import jjfactory.diary.TestEntityFactory
import jjfactory.diary.common.exception.BadCredentialsException
import jjfactory.diary.common.exception.ResourceNotFoundException
import jjfactory.diary.domain.AdminUserCommand
import jjfactory.diary.domain.auth.AuthServiceImpl
import jjfactory.diary.infrastructure.user.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class AuthServiceImplTest {

    @Autowired
    lateinit var authService: AuthServiceImpl

    @Autowired
    lateinit var userRepository: UserRepository

    private val testEntityFactory = TestEntityFactory()


    @Test
    @Transactional
    fun joinSuccess() {
        val command = AdminUserCommand.Create(
            lastName = "lee",
            firstName = "jj",
            phone = "01012341234",
            email = "wogud2@naver.com",
            password = "1234"
        )

        val result = authService.join(command)
        assertThat(result).isNotNull()
    }

    @Test
    fun `없는 이메일로 로그인하면 익셉션`() {
        assertThatThrownBy {
            authService.login(
                email = "nonExistEmail",
                password = "1234"
            )
        }.isInstanceOf(ResourceNotFoundException::class.java)
    }

    @Test
    @Transactional
    fun `비밀번호 틀리면 익셉션`() {
        val user = testEntityFactory.ofUser()
        userRepository.save(user)

        assertThatThrownBy {
            authService.login(
                email = user.email,
                password = user.password + "1"
            )
        }.isInstanceOf(BadCredentialsException::class.java)
    }

    @Test
    @Transactional
    fun `로그인 성공`() {
        val command = AdminUserCommand.Create(
            lastName = "lee",
            firstName = "jj",
            phone = "01012341234",
            email = "wogud2@naver.com",
            password = "1234"
        )

        authService.join(command)

        val response = authService.login(
            email = command.email,
            password = command.password
        )

        assertThat(response.accessToken).isNotNull
        assertThat(response.refreshToken).isNotNull

        println("response.accessToken = ${response.accessToken}")
        println("response.refreshToken = ${response.refreshToken}")
    }

}