package jjfactory.diary.presentation

import jjfactory.diary.common.response.CommonResponse
import jjfactory.diary.config.security.AuthSupporter
import jjfactory.diary.domain.diary.DiaryCommand
import jjfactory.diary.domain.report.ReportService
import org.springframework.web.bind.annotation.*

@RequestMapping("/reports")
@RestController
class ReportApi(
    private val reportService: ReportService
) {

    @PostMapping("/{id}/report")
    fun report(@PathVariable id: Long, @RequestBody command: DiaryCommand.Report): CommonResponse<Long> {
        val userId = AuthSupporter.getLoginUserId()
        return CommonResponse(
            reportService.report(diaryId = id, reporterId = userId, command = command)
        )
    }
}