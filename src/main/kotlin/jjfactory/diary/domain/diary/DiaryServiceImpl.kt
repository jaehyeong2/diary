package jjfactory.diary.domain.diary

import jjfactory.diary.domain.user.UserReader
import jjfactory.diary.infrastructure.user.DiaryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DiaryServiceImpl(
    private val diaryRepository: DiaryRepository,
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
    override fun modify(userId: Long, command: DiaryCommand.Modify) {
        TODO("Not yet implemented")
    }
}