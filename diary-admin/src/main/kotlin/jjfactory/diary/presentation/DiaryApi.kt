package jjfactory.diary.presentation

import io.swagger.v3.oas.annotations.Operation
import jjfactory.diary.common.response.CommonPagingResponse
import jjfactory.diary.common.response.CommonResponse
import jjfactory.diary.common.response.PagingResponse
import jjfactory.diary.config.security.AuthSupporter
import jjfactory.diary.domain.diary.Diary
import jjfactory.diary.domain.diary.DiaryInfo
import jjfactory.diary.domain.diary.DiaryService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/diary")
class DiaryApi(
    private val diaryService: DiaryService,
) {

    @GetMapping
    fun getDiaryList(@PageableDefault pageable: Pageable, @RequestParam accessLevel: Diary.AccessLevel): CommonPagingResponse<DiaryInfo.List?> {
        val userId = AuthSupporter.getLoginUserId()
        return CommonPagingResponse(PagingResponse( diaryService.getDiaryPage(
            pageable = pageable,
            accessLevel = accessLevel
        )))
    }

    @Operation(summary = "일기 조회")
    @GetMapping("/{id}")
    fun getDiary(@PathVariable id: Long): CommonResponse<DiaryInfo.Detail> {
        val userId = AuthSupporter.getLoginUserId()
        return CommonResponse(diaryService.getDiary(id, userId))
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
}