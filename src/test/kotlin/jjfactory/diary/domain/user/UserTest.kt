package jjfactory.diary.domain.user

import jjfactory.diary.TestEntityFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UserTest {
    private val testEntityFactory = TestEntityFactory()

    @Test
    fun pointUp() {
        val user = testEntityFactory.ofUser()

        user.pointUp(5)

        assertThat(user.point).isEqualTo(5)
    }

    @Test
    fun pointDown() {
        val user = testEntityFactory.ofUser()
        user.pointUp(10)

        user.pointDown(7)
        assertThat(user.point).isEqualTo(3)
        user.pointDown(7)
        assertThat(user.point).isEqualTo(0)
    }
}