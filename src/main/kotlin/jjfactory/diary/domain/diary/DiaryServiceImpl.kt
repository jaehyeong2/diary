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
}