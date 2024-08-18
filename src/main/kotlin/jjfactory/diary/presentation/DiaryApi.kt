package jjfactory.diary.presentation

import io.swagger.v3.oas.annotations.Operation
import jjfactory.diary.common.response.CommonResponse
import jjfactory.diary.config.security.AuthSupporter
import jjfactory.diary.config.security.UserAuthentication
import jjfactory.diary.domain.diary.DiaryCommand
import jjfactory.diary.domain.diary.DiaryInfo
import jjfactory.diary.domain.diary.DiaryService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/diary")
class DiaryApi(
    private val diaryService: DiaryService,
) {

    //todo paging
    @GetMapping
    fun getPublicDiaryList(): CommonResponse<List<DiaryInfo.List>>{
        return CommonResponse(diaryService.getPublicDiaryList())
    }

    @Operation(summary = "일기 조회")
    @GetMapping("/{id}")
    fun getDiary(@PathVariable id: Long): CommonResponse<DiaryInfo.Detail> {
        val userId = AuthSupporter.getLoginUserId()
        return CommonResponse(diaryService.getDiary(id, userId))
    }

    @Operation(summary = "일기 작성")
    @PostMapping
    fun writeDiary(@RequestBody command: DiaryCommand.Create): CommonResponse<Long> {
        val userId = AuthSupporter.getLoginUserId()

        return CommonResponse(
            diaryService.write(
                userId = userId,
                command = command
            )
        )
    }

    @Operation(summary = "일기 수정")
    @PatchMapping("/{id}")
    fun modify(@PathVariable id: Long, @RequestBody command: DiaryCommand.Modify): CommonResponse<Unit> {
        val userId = AuthSupporter.getLoginUserId()

        diaryService.modify(
            userId = userId,
            id = id,
            command = command
        )

        return CommonResponse.OK
    }

    @Operation(summary = "일기 삭제")
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): CommonResponse<Unit> {
        val userId = AuthSupporter.getLoginUserId()

        diaryService.delete(
            userId = userId,
            id = id,
        )

        return CommonResponse.OK
    }

    @PostMapping("/{id}/report")
    fun report(@PathVariable id: Long, @RequestBody command: DiaryCommand.Report): CommonResponse<Long> {
        val userId = AuthSupporter.getLoginUserId()

        return CommonResponse(
            diaryService.report(id = id, reporterId = userId, command = command)
        )
    }

    @Operation(summary = "일기 프라이빗으로 변경")
    @PostMapping("/{id}/hide")
    fun hide(@PathVariable id: Long): CommonResponse<Unit> {
        val userId = AuthSupporter.getLoginUserId()

        diaryService.hide(
            userId = userId,
            id = id,
        )

        return CommonResponse.OK
    }

    @PostMapping("/{id}/open")
    fun open(@PathVariable id: Long): CommonResponse<Unit> {
        val userId = AuthSupporter.getLoginUserId()

        diaryService.openToAll(
            userId = userId,
            id = id,
        )

        return CommonResponse.OK
    }
}