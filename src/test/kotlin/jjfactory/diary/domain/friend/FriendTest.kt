package jjfactory.diary.domain.friend

import jjfactory.diary.domain.user.User
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class FriendTest{

    @Test
    fun acceptRequest(){
        val user = User(
            lastName = "lee",
            firstName = "jj",
            phone = "01012341234",
            gender = User.Gender.MALE,
            email = "wogud2@naver.com",
            username = "kkk"
        )

        val user2 = User(
            lastName = "kim",
            firstName = "jj",
            phone = "01012341234",
            gender = User.Gender.MALE,
            email = "wogud2@naver.com",
            username = "kkk"
        )

        val friend = Friend(
            sender = user,
            receiver = user2
        )

        assertThat(friend.status).isEqualTo(Friend.Status.REQUESTED)
        friend.accept()

        assertThat(friend.status).isEqualTo(Friend.Status.ACCEPTED)
    }
}