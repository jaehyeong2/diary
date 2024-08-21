package jjfactory.diary.presentation

import jjfactory.diary.common.response.CommonPagingResponse
import jjfactory.diary.common.response.PagingResponse
import jjfactory.diary.config.security.AuthSupporter
import jjfactory.diary.domain.report.ReportInfo
import jjfactory.diary.domain.report.ReportService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/report")
class ReportApi(
    private val reportService: ReportService
) {

    @GetMapping
    fun getReports(@PageableDefault pageable: Pageable): CommonPagingResponse<ReportInfo.Detail?> {
        AuthSupporter.getLoginUserId()
        return CommonPagingResponse(PagingResponse(reportService.getReportList(pageable)))
    }
}