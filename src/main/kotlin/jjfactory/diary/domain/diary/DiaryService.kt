package jjfactory.diary.domain.diary

interface DiaryService {
    fun write(userId: Long, command: DiaryCommand.Create): Long
    fun modify(userId: Long, id: Long, command: DiaryCommand.Modify)
}