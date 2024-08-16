package jjfactory.diary.presentation

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

    @GetMapping("/{id}")
    fun getDiary(@PathVariable id: Long): CommonResponse<DiaryInfo.Detail> {
        val userAuthentication = SecurityContextHolder.getContext().authentication as UserAuthentication
        return CommonResponse(diaryService.getDiary(id, userAuthentication.getUserId()))
    }

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

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): CommonResponse<Unit> {
        diaryService.delete(
            userId = 2L,
            id = id,
        )

        return CommonResponse.OK
    }

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