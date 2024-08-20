package jjfactory.diary.domain

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jjfactory.diary.TestEntityFactory
import jjfactory.diary.common.exception.AccessForbiddenException
import jjfactory.diary.common.exception.DuplicateRequestException
import jjfactory.diary.config.CacheConfig.*
import jjfactory.diary.domain.diary.Diary
import jjfactory.diary.domain.diary.DiaryCommand
import jjfactory.diary.domain.diary.DiaryInfo
import jjfactory.diary.domain.diary.DiaryServiceImpl
import jjfactory.diary.infrastructure.diary.DiaryReaderImpl
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cache.CacheManager
import org.springframework.transaction.annotation.Transactional

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

    @Autowired
    lateinit var cacheManager: CacheManager

    @BeforeEach
    fun setUp() {
        cacheManager.getCache(CacheType.DIARY_INFO.cacheName)?.clear()
    }

    @Test
    @Transactional
    fun writeSuccess() {
        val user = testEntityFactory.ofUser()

        entityManager.persist(user)

        val command = DiaryCommand.Create(
            content = "안녕 오늘 일기야",
            title = "안녕 오늘 일기야",
            type = Diary.Type.DAILY,
            accessLevel = Diary.AccessLevel.PRIVATE
        )

        diaryService.write(
            userId = user.id!!,
            command = command
        )
    }

//    @Test
//    @Transactional
//    fun reportSuccess() {
//        val user = testEntityFactory.ofUser()
//        entityManager.persist(user)
//
//        val command = DiaryCommand.Create(
//            content = "안녕 오늘 일기야",
//            title = "안녕 오늘 일기야",
//            type = Diary.Type.DAILY,
//            accessLevel = Diary.AccessLevel.PRIVATE
//        )
//
//        val diaryId = diaryService.write(
//            userId = user.id!!,
//            command = command
//        )
//
//        val result = diaryService.report(
//            id = diaryId,
//            reporterId = 3000L,
//            command = DiaryCommand.Report(reason = "음란물")
//        )
//
//        assertThat(result).isNotNull
//    }

//    @Test
//    @Transactional
//    fun `셀프 신고 불가`() {
//        val user = testEntityFactory.ofUser()
//        entityManager.persist(user)
//
//        val command = DiaryCommand.Create(
//            content = "안녕 오늘 일기야",
//            title = "안녕 오늘 일기야",
//            type = Diary.Type.DAILY,
//            accessLevel = Diary.AccessLevel.PRIVATE
//        )
//
//        val diaryId = diaryService.write(
//            userId = user.id!!,
//            command = command
//        )
//
//        assertThatThrownBy {
//            diaryService.report(
//                id = diaryId,
//                reporterId = user.id!!,
//                command = DiaryCommand.Report(reason = "음란물")
//            )
//        }.isInstanceOf(IllegalArgumentException::class.java)
//    }

//    @Test
//    @Transactional
//    fun `중복 신고 불가`() {
//        val user = testEntityFactory.ofUser()
//        entityManager.persist(user)
//
//        val command = DiaryCommand.Create(
//            content = "안녕 오늘 일기야",
//            title = "안녕 오늘 일기야",
//            type = Diary.Type.DAILY,
//            accessLevel = Diary.AccessLevel.PRIVATE
//        )
//
//        val diaryId = diaryService.write(
//            userId = user.id!!,
//            command = command
//        )
//
//        diaryService.report(
//            id = diaryId,
//            reporterId = 3000L,
//            command = DiaryCommand.Report(reason = "음란물")
//        )
//
//        assertThatThrownBy {
//            diaryService.report(
//                id = diaryId,
//                reporterId = 3000L,
//                command = DiaryCommand.Report(reason = "음란물")
//            )
//        }.isInstanceOf(DuplicateRequestException::class.java)
//    }

    @Test
    @Transactional
    fun modifySuccess() {
        val user = testEntityFactory.ofUser()

        entityManager.persist(user)

        val command = DiaryCommand.Create(
            content = "안녕 오늘 일기야",
            title = "안녕 오늘 일기야",
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
            title = "안녕 오늘 일기야",
            accessLevel = Diary.AccessLevel.PRIVATE
        )

        diaryService.modify(user.id!!, diaryId, command2)

        val diary = diaryReader.getOrThrow(diaryId)
        assertThat(diary.type).isEqualTo(Diary.Type.DAILY)
        assertThat(diary.content).isEqualTo(command2.content)
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
            title = "안녕 오늘 일기야",
            accessLevel = Diary.AccessLevel.PRIVATE
        )

        val diaryId = diaryService.write(
            userId = user.id!!,
            command = command
        )

        val command2 = DiaryCommand.Modify(
            content = "안녕 오늘 일기야",
            type = Diary.Type.DAILY,
            title = "안녕 오늘 일기야",
            accessLevel = Diary.AccessLevel.PRIVATE
        )

        assertThatThrownBy {
            diaryService.modify(user2.id!!, diaryId, command2)
        }.isInstanceOf(AccessForbiddenException::class.java)
    }

    @Transactional
    @Test
    fun `저장 후 바로 조회 시 캐시 null`() {
        val user = testEntityFactory.ofUser()
        entityManager.persist(user)
        val diary = testEntityFactory.ofPublicDiary(userId = user.id)
        entityManager.persist(diary)

        val diaryCache =
            cacheManager.getCache(CacheType.DIARY_INFO.cacheName)?.get(diary.id!!, DiaryInfo.Detail::class.java)

        assertThat(diaryCache).isNull()

        val response = diaryService.getDiary(
            id = diary.id!!,
            userId = user.id!!
        )

        assertThat(response.content).isEqualTo(diary.content)
        assertThat(response.username).isEqualTo(user.username)
    }

    @Transactional
    @Test
    fun `캐시에 저장되고 조회 시 캐시에서 조회`() {
        val user = testEntityFactory.ofUser()
        entityManager.persist(user)
        val diary = testEntityFactory.ofPublicDiary(userId = user.id)
        entityManager.persist(diary)

        diaryService.getDiary(
            id = diary.id!!,
            userId = user.id!!
        )

        // 수정 위한 flush
        diary.modify(
            content = "modified",
            type = diary.type,
            title = "안녕 오늘 일기야",
            accessLevel = Diary.AccessLevel.ALL
        )
        entityManager.flush()

        val response2 = diaryService.getDiary(
            id = diary.id!!,
            userId = user.id!!
        )

        val diaryCache =
            cacheManager.getCache(CacheType.DIARY_INFO.cacheName)?.get(diary.id!!, DiaryInfo.Detail::class.java)
        assertThat(diaryCache).isNotNull

        assertThat(response2.content).isEqualTo(diaryCache!!.content)
    }

    @Transactional
    @Test
    fun `모두 공개 아니면 다른사람이 조회하면 익셉션`() {
        val user = testEntityFactory.ofUser()
        entityManager.persist(user)

        val user2 = testEntityFactory.ofUser()
        entityManager.persist(user2)

        val diary = testEntityFactory.ofPrivateDiary(userId = user.id)
        entityManager.persist(diary)

        assertThatThrownBy {
            diaryService.getDiary(
                id = diary.id!!,
                userId = user2.id!!
            )
        }.isInstanceOf(AccessForbiddenException::class.java)
    }

}