package jjfactory.diary.domain.diary

import org.springframework.data.domain.Page

interface DiaryService {
    fun write(userId: Long, command: DiaryCommand.Create): Long
    fun modify(userId: Long, id: Long, command: DiaryCommand.Modify)
    fun delete(userId: Long, id: Long)
    fun getDiary(id: Long, userId: Long): DiaryInfo.Detail
    fun hide(userId: Long, id: Long)
    fun getDiaryPage(): Page<DiaryInfo.Detail>
    fun openToAll(userId: Long, id: Long)
    fun getPublicDiaryList(): List<DiaryInfo.List>
    fun report(id: Long, reporterId: Long, command: DiaryCommand.Report): Long
}