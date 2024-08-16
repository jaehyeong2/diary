package jjfactory.diary.domain.friend

import jjfactory.diary.TestEntityFactory
import jjfactory.diary.common.exception.AccessForbiddenException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class FriendTest {
    private val testEntityFactory = TestEntityFactory()

    @Test
    fun `요청 받은 대상이 아니면 수락 불가능`() {
        val friend = Friend(
            senderId = 2L,
            receiverId = 3L
        )

        assertThatThrownBy {
            friend.accept(2L)
        }.isInstanceOf(AccessForbiddenException::class.java)
    }

    @Test
    fun `친구 요청 수락 성공`() {
        val friend = Friend(
            senderId = 2L,
            receiverId = 3L
        )

        assertThat(friend.status).isEqualTo(Friend.Status.REQUESTED)
        friend.accept(3L)

        assertThat(friend.status).isEqualTo(Friend.Status.ACCEPTED)
    }
}