package jjfactory.diary.presentation

import io.swagger.v3.oas.annotations.Operation
import jjfactory.diary.application.CommentFacade
import jjfactory.diary.common.response.CommonPagingResponse
import jjfactory.diary.common.response.CommonResponse
import jjfactory.diary.common.response.PagingResponse
import jjfactory.diary.config.security.AuthSupporter
import jjfactory.diary.domain.diary.comment.CommentCommand
import jjfactory.diary.domain.diary.comment.CommentInfo
import jjfactory.diary.domain.diary.comment.CommentService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/comment")
class CommentApi(
    private val commentFacade: CommentFacade,
    private val commentService: CommentService
) {

    @GetMapping
    fun getComments(@RequestParam diaryId: Long, @PageableDefault pageable: Pageable): CommonPagingResponse<CommentInfo.List?> {
        return CommonPagingResponse(PagingResponse(
            commentService.getPageByDiaryId(diaryId = diaryId, pageable = pageable)
        ))
    }

    @Operation(summary = "댓글 작성")
    @PostMapping
    fun write(@RequestBody command: CommentCommand.Create): CommonResponse<Long> {
        val userId = AuthSupporter.getLoginUserId()
        val result = commentFacade.writeComment(
            userId = userId,
            command = command
        )

        return CommonResponse(result)
    }

    @Operation(summary = "댓글 수정")
    @PatchMapping("/{id}")
    fun modify(@PathVariable id: Long, @RequestBody command: CommentCommand.Modify): CommonResponse<Unit> {
        val userId = AuthSupporter.getLoginUserId()

        commentService.modify(
            id = id,
            userId = userId,
            command = command
        )

        return CommonResponse.OK
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): CommonResponse<Unit> {
        val userId = AuthSupporter.getLoginUserId()
        commentService.deleteById(id = id, userId = userId)
        return CommonResponse.OK
    }
}