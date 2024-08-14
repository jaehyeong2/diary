package jjfactory.diary.infrastructure.diary

import jjfactory.diary.domain.diary.Diary
import jjfactory.diary.domain.diary.DiaryReader
import jjfactory.diary.common.exception.ResourceNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class DiaryReaderImpl(
    private val dairyRepository: DiaryRepository
) : DiaryReader {
    override fun get(id: Long): Diary? {
        return dairyRepository.findByIdOrNull(id)
    }

    override fun getOrThrow(id: Long): Diary {
        return dairyRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException("diary not found")
    }
}