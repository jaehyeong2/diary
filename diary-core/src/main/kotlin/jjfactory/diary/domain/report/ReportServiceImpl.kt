package jjfactory.diary.domain.report

import jjfactory.diary.common.exception.DuplicateRequestException
import jjfactory.diary.domain.diary.DiaryCommand
import jjfactory.diary.domain.diary.DiaryReader
import jjfactory.diary.infrastructure.report.DiaryReportRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class ReportServiceImpl(
    private val diaryReportRepository: DiaryReportRepository,
    private val reportReader: ReportReader,
    private val diaryReader: DiaryReader
) : ReportService{
    override fun report(diaryId: Long, reporterId: Long, command: DiaryCommand.Report): Long {
        val diary = diaryReader.getOrThrow(diaryId)

        diaryReportRepository.findByDiaryIdAndReporterId(diaryId = diary.id!!, reporterId = reporterId)?.let {
            throw DuplicateRequestException()
        }

        val initReport = DiaryReport(
            diary = diary,
            reporterId = reporterId,
            reason = command.reason
        )

        initReport.validate()

        return diaryReportRepository.save(initReport).id!!
    }

    @Transactional(readOnly = true)
    override fun getReportList(pageable: Pageable): Page<ReportInfo.Detail?> {
        return reportReader.getDiaryReportInfoPage(pageable)
    }
}