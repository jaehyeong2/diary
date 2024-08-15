package jjfactory.diary.domain.diary

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jjfactory.diary.TestEntityFactory
import jjfactory.diary.domain.user.User
import jjfactory.diary.common.exception.AccessForbiddenException
import jjfactory.diary.infrastructure.diary.DiaryReaderImpl
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.lang.reflect.Member.PUBLIC

@SpringBootTest
class DiaryServiceImplTest(

) {
    private val testEntityFactory = TestEntityFactory()

    @Autowired
    lateinit var diaryService: DiaryServiceImpl
    @PersistenceContext
    lateinit var entityManager: EntityManager
    @Autowired
    lateinit var diaryReader: DiaryReaderImpl
    @Test
    @Transactional
    fun writeSuccess() {
        val user = testEntityFactory.ofUser()

        entityManager.persist(user)

        val command = DiaryCommand.Create(
            content = "안녕 오늘 일기야",
            type = Diary.Type.DAILY,
            accessLevel = Diary.AccessLevel.PRIVATE
        )

        diaryService.write(
            userId = user.id!!,
            command = command
        )
    }

    @Test
    @Transactional
    fun modifySuccess() {
        val user = testEntityFactory.ofUser()

        entityManager.persist(user)

        val command = DiaryCommand.Create(
            content = "안녕 오늘 일기야",
            type = Diary.Type.DAILY,
            accessLevel = Diary.AccessLevel.PRIVATE
        )

        val diaryId = diaryService.write(
            userId = user.id!!,
            command = command
        )

        val command2 = DiaryCommand.Modify(
            content = "안녕 오늘 일기야",
            type = Diary.Type.DAILY,
            accessLevel = Diary.AccessLevel.PRIVATE
        )

        diaryService.modify(user.id!!, diaryId, command2)

        val diary = diaryReader.getOrThrow(diaryId)
        Assertions.assertThat(diary.type).isEqualTo(Diary.Type.DAILY)
        Assertions.assertThat(diary.content).isEqualTo(command2.content)
    }

    @Test
    @Transactional
    fun `본인 아니면 수정하려하면 익셉션`() {
        val user = testEntityFactory.ofUser()

        entityManager.persist(user)

        val user2 = testEntityFactory.ofUser()

        entityManager.persist(user2)

        val command = DiaryCommand.Create(
            content = "안녕 오늘 일기야",
            type = Diary.Type.DAILY,
            accessLevel = Diary.AccessLevel.PRIVATE
        )

        val diaryId = diaryService.write(
            userId = user.id!!,
            command = command
        )

        val command2 = DiaryCommand.Modify(
            content = "안녕 오늘 일기야",
            type = Diary.Type.DAILY,
            accessLevel = Diary.AccessLevel.PRIVATE
        )

        Assertions.assertThatThrownBy {
            diaryService.modify(user2.id!!, diaryId, command2)
        }.isInstanceOf(AccessForbiddenException::class.java)
    }

}