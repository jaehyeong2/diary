package jjfactory.diary.domain.report

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ReportReader {
    fun getDiaryReportOrThrow(id: Long): DiaryReport
    fun getDiaryReport(id: Long): DiaryReport?
    fun getDiaryReportPage(pageable: Pageable): Page<DiaryReport?>
    fun getDiaryReportInfoPage(pageable: Pageable): Page<ReportInfo.Detail?>
}