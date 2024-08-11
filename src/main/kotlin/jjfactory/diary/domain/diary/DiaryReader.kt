package jjfactory.diary.domain.diary

interface DiaryReader {
    fun get(id: Long): Diary?
    fun getOrThrow(id: Long): Diary
}