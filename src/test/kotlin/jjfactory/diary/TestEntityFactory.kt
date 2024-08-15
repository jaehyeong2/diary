package jjfactory.diary

import jjfactory.diary.domain.user.User

class TestEntityFactory {

    fun ofUser(id: Long? = null): User {
        return User(
            lastName = "lee",
            firstName = "jj",
            phone = "01012341234",
            gender = User.Gender.MALE,
            email = "wogud2@naver.com",
            username = "kkk",
            password = "1234"
        )
    }
}