package jjfactory.diary.domain.diary

import jjfactory.diary.domain.user.UserReader
import jjfactory.diary.exception.AccessForbiddenException
import jjfactory.diary.infrastructure.diary.DiaryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DiaryServiceImpl(
    private val diaryRepository: DiaryRepository,
    private val diaryReader: DiaryReader,
    private val userReader: UserReader
) : DiaryService {

    @Transactional
    override fun write(userId: Long, command: DiaryCommand.Create): Long {
        val user = userReader.getOrThrow(userId)
        val initDiary = command.toEntity(user)

        val diary = diaryRepository.save(initDiary)

        user.pointUp(3)
        return diary.id!!
    }

    @Transactional
    override fun modify(userId: Long, id: Long, command: DiaryCommand.Modify) {
        val diary = diaryReader.getOrThrow(id)
        if (diary.user.id != userId) throw AccessForbiddenException()

        diary.modify(
            type = command.type,
            content = command.content
        )
    }

    @Transactional
    override fun delete(userId: Long, id: Long) {
        val diary = diaryReader.getOrThrow(id)
        val user = userReader.getOrThrow(userId)
        if (diary.user.id != user.id) throw AccessForbiddenException()

        diaryRepository.delete(diary)
        user.pointDown(3)
    }
}