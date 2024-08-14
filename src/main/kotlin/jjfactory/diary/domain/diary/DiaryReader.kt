package jjfactory.diary.domain.diary

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface DiaryReader {
    fun get(id: Long): Diary?
    fun getOrThrow(id: Long): Diary
    fun getDiaryPage(pageable: Pageable): Page<Diary?>
}