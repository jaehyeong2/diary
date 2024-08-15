package jjfactory.diary.infrastructure.diary

import jjfactory.diary.domain.diary.Diary
import jjfactory.diary.domain.diary.DiaryReader
import jjfactory.diary.common.exception.ResourceNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class DiaryReaderImpl(
    private val diaryRepository: DiaryRepository
) : DiaryReader {
    override fun get(id: Long): Diary? {
        return diaryRepository.findByIdOrNull(id)
    }

    override fun getOrThrow(id: Long): Diary {
        return diaryRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException("diary not found")
    }

    override fun getPage(pageable: Pageable): Page<Diary?> {
        return diaryRepository.findPage(pageable) {
            select(
                entity(Diary::class)
            ).from(
                entity(Diary::class)
            )
        }
    }
}