package jjfactory.diary.domain.diary

import jjfactory.diary.common.exception.AccessForbiddenException
import jjfactory.diary.common.exception.DuplicateRequestException
import jjfactory.diary.domain.report.DiaryReport
import jjfactory.diary.domain.user.UserReader
import jjfactory.diary.infrastructure.diary.DiaryRepository
import jjfactory.diary.infrastructure.report.DiaryReportRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class DiaryServiceImpl(
    private val diaryRepository: DiaryRepository,
    private val diaryReader: DiaryReader,
    private val userReader: UserReader,
    private val diaryReportRepository: DiaryReportRepository
) : DiaryService {

    override fun write(userId: Long, command: DiaryCommand.Create): Long {
        val user = userReader.getOrThrow(userId)
        val initDiary = command.toEntity(user.id!!)

        val diary = diaryRepository.save(initDiary)

        user.pointUp(3)
        return diary.id!!
    }

    @CacheEvict(cacheNames = ["diary_detail"], key = "#id")
    override fun modify(userId: Long, id: Long, command: DiaryCommand.Modify) {
        val diary = diaryReader.getOrThrow(id)
        if (diary.userId != userId) throw AccessForbiddenException()

        diary.modify(
            type = command.type,
            content = command.content,
            title = command.title,
            accessLevel = command.accessLevel
        )
    }

    override fun delete(userId: Long, id: Long) {
        val diary = diaryReader.getOrThrow(id)
        val user = userReader.getOrThrow(userId)
        if (diary.userId != user.id) throw AccessForbiddenException()

        diaryRepository.delete(diary)
        user.pointDown(3)
    }

    @Cacheable(cacheNames = ["diary_detail"], key = "#id")
    @Transactional(readOnly = true)
    override fun getDiary(id: Long, userId: Long): DiaryInfo.Detail {
        val diary = diaryReader.getOrThrow(id)

        if (diary.accessLevel == Diary.AccessLevel.PRIVATE && diary.userId != userId) {
            throw AccessForbiddenException()
        }

        val owner = userReader.getOrThrow(diary.userId)

        return DiaryInfo.Detail(
            id = diary.id!!,
            type = diary.type,
            title = diary.title,
            content = diary.content,
            userId = owner.id!!,
            username = owner.username,
            createdAt = diary.createdAt,
            updatedAt = diary.updatedAt
        )

    }

    override fun getPublicDiaryList(): List<DiaryInfo.List> {
        val diaries = diaryRepository.findAllByAccessLevelIsOrderByCreatedAtDesc(accessLevel = Diary.AccessLevel.ALL)

        val writerIds = diaries.map { it.userId }
        val userNameMap = userReader.getByIdIn(writerIds).associate { it.id to it.username }

        //todo join paging

        return diaries.map {
            DiaryInfo.List(
                id = it.id!!,
                title = it.title,
                type = it.type,
                userId = it.userId,
                username = userNameMap[it.id] ?: "",
                createdAt = it.createdAt,
                updatedAt = it.updatedAt
            )
        }
    }

    override fun report(id: Long, reporterId: Long, command: DiaryCommand.Report): Long {
        val diary = diaryReader.getOrThrow(id)
        if (diary.userId == reporterId) throw IllegalArgumentException()

        diaryReportRepository.findByDiaryIdAndReporterId(diaryId = diary.id!!, reporterId = reporterId)?.let {
            throw DuplicateRequestException()
        }

        val initReport = DiaryReport(
            diaryId = diary.id,
            reporterId = reporterId,
            reason = command.reason
        )

        return diaryReportRepository.save(initReport).id!!
    }

    @CacheEvict(cacheNames = ["diary_detail"], key = "#id")
    override fun openToAll(userId: Long, id: Long) {
        val diary = diaryReader.getOrThrow(id)
        val owner = userReader.getOrThrow(diary.userId)

        if (owner.id != userId) {
            throw AccessForbiddenException()
        }
        diary.openToAll()
    }

    @CacheEvict(cacheNames = ["diary_detail"], key = "#id")
    override fun hide(userId: Long, id: Long) {
        val diary = diaryReader.getOrThrow(id)
        val owner = userReader.getOrThrow(diary.userId)

        if (owner.id != userId) {
            throw AccessForbiddenException()
        }

        diary.hide()
    }

    override fun getDiaryPage(): Page<DiaryInfo.Detail> {
        TODO("Not yet implemented")
    }
}