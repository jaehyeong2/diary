package jjfactory.diary.domain.diary

import jjfactory.diary.domain.user.UserReader
import jjfactory.diary.exception.AccessForbiddenException
import jjfactory.diary.infrastructure.diary.DiaryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class DiaryServiceImpl(
    private val diaryRepository: DiaryRepository,
    private val diaryReader: DiaryReader,
    private val userReader: UserReader
) : DiaryService {

    override fun write(userId: Long, command: DiaryCommand.Create): Long {
        val user = userReader.getOrThrow(userId)
        val initDiary = command.toEntity(user)

        val diary = diaryRepository.save(initDiary)

        user.pointUp(3)
        return diary.id!!
    }

    override fun modify(userId: Long, id: Long, command: DiaryCommand.Modify) {
        val diary = diaryReader.getOrThrow(id)
        if (diary.user.id != userId) throw AccessForbiddenException()

        diary.modify(
            type = command.type,
            content = command.content
        )
    }

    override fun delete(userId: Long, id: Long) {
        val diary = diaryReader.getOrThrow(id)
        val user = userReader.getOrThrow(userId)
        if (diary.user.id != user.id) throw AccessForbiddenException()

        diaryRepository.delete(diary)
        user.pointDown(3)
    }

    @Transactional(readOnly = true)
    override fun getDiary(id: Long, userId: Long): DiaryInfo.Detail {
        val diary = diaryReader.getOrThrow(id)
        val owner = diary.user

        if (diary.type == Diary.Type.PRIVATE && owner.id != userId){
            throw AccessForbiddenException()
        }

        return DiaryInfo.Detail(
            id = diary.id!!,
            type = diary.type,
            content = diary.content,
            userId = owner.id!!,
            username = owner.username,
            createdAt = diary.createdAt,
            updatedAt = diary.updatedAt
        )

    }

    override fun open(id: Long) {
        diaryReader.getOrThrow(id).open()
    }

    override fun hide(id: Long) {
        diaryReader.getOrThrow(id).hide()
    }
}