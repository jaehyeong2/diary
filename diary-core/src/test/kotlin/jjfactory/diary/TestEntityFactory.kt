package jjfactory.diary

import jjfactory.diary.domain.diary.Diary
import jjfactory.diary.domain.diary.comment.Comment
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

    fun ofPrivateDiary(userId: Long? = null): Diary {
        return Diary(
            userId = userId ?: 2L,
            title = "test title",
            content = "안녕 오늘 일기야",
            type = Diary.Type.DAILY,
            accessLevel = Diary.AccessLevel.PRIVATE
        )
    }

    fun ofPublicDiary(userId: Long? = null): Diary {
        return Diary(
            userId = userId ?: 2L,
            title = "test title",
            content = "안녕 오늘 일기야",
            type = Diary.Type.DAILY,
            accessLevel = Diary.AccessLevel.ALL
        )
    }

    fun ofRootComment(userId: Long? = null, diaryId: Long? = null): Comment {
        return Comment(
            userId = userId ?: 2L,
            diaryId = diaryId ?: 3L,
            content = "comment test"
        )
    }
}