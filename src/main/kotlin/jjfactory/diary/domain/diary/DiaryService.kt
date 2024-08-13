package jjfactory.diary.domain.diary

interface DiaryService {
    fun write(userId: Long, command: DiaryCommand.Create): Long
    fun modify(userId: Long, id: Long, command: DiaryCommand.Modify)
    fun delete(userId: Long, id: Long)
    fun getDiary(id: Long, userId: Long): DiaryInfo.Detail
    fun open(id: Long)
    fun hide(id: Long)
}