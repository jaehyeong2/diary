package jjfactory.diary.domain.user

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UserTest {
    @Test
    fun pointUp() {
        val user = User(
            lastName = "lee",
            firstName = "jj",
            phone = "01012341234",
            gender = User.Gender.MALE,
            email = "wogud2@naver.com",
            username = "kkk"
        )

        user.pointUp(5)

        assertThat(user.point).isEqualTo(5)
    }

    @Test
    fun pointDown() {
        val user = User(
            lastName = "lee",
            firstName = "jj",
            phone = "01012341234",
            gender = User.Gender.MALE,
            email = "wogud2@naver.com",
            username = "kkk",
            point = 10
        )

        user.pointDown(7)
        assertThat(user.point).isEqualTo(3)
        user.pointDown(7)
        assertThat(user.point).isEqualTo(0)
    }
}