package jjfactory.diary.infrastructure.report

import jjfactory.diary.common.exception.ResourceNotFoundException
import jjfactory.diary.domain.report.DiaryReport
import jjfactory.diary.domain.report.ReportInfo
import jjfactory.diary.domain.report.ReportReader
import jjfactory.diary.domain.user.User
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
        return diaryReportRepository.findPage(pageable) {
            select(
                entity(DiaryReport::class)
            ).from(
                entity(DiaryReport::class)
            ).orderBy(
                path(DiaryReport::createdAt).desc()
            )
        }
    }

    override fun getDiaryReportInfoPage(pageable: Pageable): Page<ReportInfo.Detail?> {
        return diaryReportRepository.findPage(pageable) {
            selectNew<ReportInfo.Detail>(
                path(DiaryReport::id).`as`(expression(Long::class, "diaryId")),
                path(DiaryReport::reporterId).`as`(expression(Long::class, "reporterId")),
                path(User::username).`as`(expression(String::class, "username")),
                path(DiaryReport::reason).`as`(expression(String::class, "content")),
            ).from(
                entity(DiaryReport::class),
                join(User::class).on(path(DiaryReport::reporterId).eq(path(User::id))),
            ).orderBy(
                path(DiaryReport::createdAt).desc()
            )
        }
    }
}