package jjfactory.diary.application

import jjfactory.diary.domain.diary.DiaryCommand
import jjfactory.diary.domain.diary.DiaryService
import jjfactory.diary.domain.point.PointService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DiaryFacade(
    private val diaryService: DiaryService,
    private val pointService: PointService,
) {

    @Transactional
    fun write(userId: Long, command: DiaryCommand.Create): Long {
        val diaryId = diaryService.write(
            userId = userId,
            command = command
        )

        pointService.storeDiaryWriteHistory(
            userId = userId,
            diaryId = diaryId
        )

        return diaryId
    }
}