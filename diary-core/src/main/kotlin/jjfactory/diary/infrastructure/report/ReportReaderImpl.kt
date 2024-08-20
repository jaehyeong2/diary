package jjfactory.diary.infrastructure.report

import jjfactory.diary.common.exception.ResourceNotFoundException
import jjfactory.diary.domain.report.DiaryReport
import jjfactory.diary.domain.report.ReportReader
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class ReportReaderImpl(
    private val diaryReportRepository: DiaryReportRepository
) : ReportReader {
    override fun getDiaryReportOrThrow(id: Long): DiaryReport {
        return diaryReportRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException("report not found")
    }

    override fun getDiaryReport(id: Long): DiaryReport? {
        return diaryReportRepository.findByIdOrNull(id)
    }

    override fun getDiaryReportPage(pageable: Pageable): Page<DiaryReport?> {
        return diaryReportRepository.findPage(pageable){
            select(
                entity(DiaryReport::class)
            ).from(
                entity(DiaryReport::class)
            ).orderBy(
                path(DiaryReport::createdAt).desc()
            )
        }
    }
}