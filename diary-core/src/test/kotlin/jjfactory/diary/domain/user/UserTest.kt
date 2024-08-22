package jjfactory.diary.domain.user

import jjfactory.diary.TestEntityFactory
import jjfactory.diary.domain.point.Point
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UserTest {
    private val testEntityFactory = TestEntityFactory()

    @Test
    fun pointUp() {
        val user = testEntityFactory.ofUser()
        user.point = Point(user = user, amount = 5)
        user.pointUp(5)
        assertThat(user.getCurrentPoint()).isEqualTo(10)
    }

    @Test
    fun pointDown() {
        val user = testEntityFactory.ofUser()
        user.pointUp(10)

        user.pointDown(7)
        assertThat(user.getCurrentPoint()).isEqualTo(3)
        user.pointDown(7)
        assertThat(user.getCurrentPoint()).isEqualTo(0)
    }
}