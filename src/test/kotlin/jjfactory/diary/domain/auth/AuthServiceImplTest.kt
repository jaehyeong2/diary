package jjfactory.diary.domain.auth

import jjfactory.diary.domain.user.DuplicateUserNameException
import jjfactory.diary.domain.user.User
import jjfactory.diary.domain.user.UserCommand
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

    @Test
    @Transactional
    fun joinSuccess() {
        val command = UserCommand.Create(
            lastName = "lee",
            firstName = "jj",
            phone = "01012341234",
            gender = User.Gender.MALE,
            email = "wogud2@naver.com",
            username = "kkk",
            password = "1234"
        )

        val result = authService.join(command)
        assertThat(result).isNotNull()
    }

    @Test
    @Transactional
    fun `중복 유저네임이면 회원가입 실패`() {
        val command = UserCommand.Create(
            lastName = "lee",
            firstName = "jj",
            phone = "01012341234",
            gender = User.Gender.MALE,
            email = "wogud2@naver.com",
            username = "kkk",
            password = "1234"
        )

        authService.join(command)


        assertThatThrownBy {
            authService.join(command)
        }.isInstanceOf(DuplicateUserNameException::class.java)
    }


}