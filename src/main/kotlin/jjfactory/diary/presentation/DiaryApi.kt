package jjfactory.diary.presentation

import jjfactory.diary.common.response.CommonResponse
import jjfactory.diary.domain.diary.DiaryCommand
import jjfactory.diary.domain.diary.DiaryService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/diary")
class DiaryApi(
    private val diaryService: DiaryService
) {

    @PostMapping
    fun writeDiary(@RequestBody command: DiaryCommand.Create): CommonResponse<Long> {
        return CommonResponse(
            diaryService.write(
                userId = 2L,
                command = command
            )
        )
    }

    @PatchMapping("/{id}")
    fun modify(@PathVariable id: Long, @RequestBody command: DiaryCommand.Modify): CommonResponse<Unit> {
        diaryService.modify(
            userId = 2L,
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