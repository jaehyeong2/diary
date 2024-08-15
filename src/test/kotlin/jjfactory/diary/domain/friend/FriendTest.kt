package jjfactory.diary.domain.friend

import jjfactory.diary.TestEntityFactory
import jjfactory.diary.domain.user.User
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class FriendTest{
    private val testEntityFactory = TestEntityFactory()

    @Test
    fun acceptRequest(){
        val user = testEntityFactory.ofUser()
        val user2 = testEntityFactory.ofUser()

        val friend = Friend(
            sender = user,
            receiver = user2
        )

        assertThat(friend.status).isEqualTo(Friend.Status.REQUESTED)
        friend.accept()

        assertThat(friend.status).isEqualTo(Friend.Status.ACCEPTED)
    }
}