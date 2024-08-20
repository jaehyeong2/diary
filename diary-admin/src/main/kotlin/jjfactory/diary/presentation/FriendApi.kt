package jjfactory.diary.presentation

import io.swagger.v3.oas.annotations.Operation
import jjfactory.diary.application.FriendFacade
import jjfactory.diary.common.response.CommonResponse
import jjfactory.diary.config.security.AuthSupporter
import jjfactory.diary.domain.friend.FriendService
import jjfactory.diary.domain.user.UserInfo
import org.springframework.web.bind.annotation.*

@RequestMapping("/friends")
@RestController
class FriendApi(
    private val friendService: FriendService,
    private val friendFacade: FriendFacade
) {

    @Operation(summary = "친구 목록")
    @GetMapping
    fun getMyFriendList(): CommonResponse<List<UserInfo.Detail>> {
        val userId = AuthSupporter.getLoginUserId()

        return CommonResponse(
            friendService.getFriendListByUserId(userId = userId)
        )
    }

    @Operation(summary = "친구 요청 목록")
    @GetMapping("/request")
    fun getMyRequestList(@RequestParam accepted: Boolean): CommonResponse<List<UserInfo.Detail>> {
        val userId = AuthSupporter.getLoginUserId()

        return CommonResponse(
            friendService.getRequestListByUserId(userId = userId, accepted)
        )
    }

    @Operation(summary = "친구 요청")
    @PostMapping
    fun sendFriendRequest(@RequestParam receiverId: Long): CommonResponse<Long> {
        val userId = AuthSupporter.getLoginUserId()

        return CommonResponse(
            friendFacade.sendRequest(senderId = userId, receiverId = receiverId)
        )
    }

    @Operation(summary = "친구 수락")
    @PostMapping("/{id}/accept")
    fun acceptRequest(@PathVariable id: Long): CommonResponse<Unit> {
        val userId = AuthSupporter.getLoginUserId()

        return CommonResponse(
            friendService.accept(userId = userId, id = id)
        )
    }
}