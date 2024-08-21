package jjfactory.diary.domain.report

import jjfactory.diary.domain.diary.DiaryCommand
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ReportService {
    fun report(diaryId: Long, reporterId: Long, command: DiaryCommand.Report): Long
    fun getReportList(pageable: Pageable): Page<ReportInfo.Detail?>
}