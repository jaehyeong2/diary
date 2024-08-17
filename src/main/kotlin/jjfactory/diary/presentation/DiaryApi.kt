package jjfactory.diary.presentation

import io.swagger.v3.oas.annotations.Operation
import jjfactory.diary.common.response.CommonResponse
import jjfactory.diary.config.security.UserAuthentication
import jjfactory.diary.domain.diary.DiaryCommand
import jjfactory.diary.domain.diary.DiaryInfo
import jjfactory.diary.domain.diary.DiaryService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/diary")
class DiaryApi(
    private val diaryService: DiaryService
) {

    @Operation(summary = "일기 조회")
    @GetMapping("/{id}")
    fun getDiary(@PathVariable id: Long): CommonResponse<DiaryInfo.Detail> {
        val userAuthentication = SecurityContextHolder.getContext().authentication as UserAuthentication
        return CommonResponse(diaryService.getDiary(id, userAuthentication.getUserId()))
    }

    @Operation(summary = "일기 작성")
    @PostMapping
    fun writeDiary(@RequestBody command: DiaryCommand.Create): CommonResponse<Long> {
        val userAuthentication = SecurityContextHolder.getContext().authentication as UserAuthentication

        return CommonResponse(
            diaryService.write(
                userId = userAuthentication.getUserId(),
                command = command
            )
        )
    }

    @Operation(summary = "일기 수정")
    @PatchMapping("/{id}")
    fun modify(@PathVariable id: Long, @RequestBody command: DiaryCommand.Modify): CommonResponse<Unit> {
        val userAuthentication = SecurityContextHolder.getContext().authentication as UserAuthentication

        diaryService.modify(
            userId = userAuthentication.getUserId(),
            id = id,
            command = command
        )

        return CommonResponse.OK
    }

    @Operation(summary = "일기 삭제")
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): CommonResponse<Unit> {
        diaryService.delete(
            userId = 2L,
            id = id,
        )

        return CommonResponse.OK
    }

    @Operation(summary = "일기 프라이빗으로 변경")
    @PostMapping("/{id}/hide")
    fun hide(@PathVariable id: Long): CommonResponse<Unit> {
        diaryService.hide(
            userId = 2L,
            id = id,
        )

        return CommonResponse.OK
    }

    @PostMapping("/{id}/open")
    fun open(@PathVariable id: Long): CommonResponse<Unit> {
        diaryService.openToAll(
            userId = 2L,
            id = id,
        )

        return CommonResponse.OK
    }
}