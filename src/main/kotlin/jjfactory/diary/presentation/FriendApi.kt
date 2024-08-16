package jjfactory.diary.presentation

import jjfactory.diary.common.response.CommonResponse
import jjfactory.diary.config.security.UserAuthentication
import jjfactory.diary.domain.friend.FriendService
import jjfactory.diary.domain.user.UserInfo
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/friend")
@RestController
class FriendApi(
    private val friendService: FriendService
) {

    @GetMapping
    fun getMyFriendList(): CommonResponse<List<UserInfo.Detail>> {
        val userAuthentication = SecurityContextHolder.getContext().authentication as UserAuthentication

        return CommonResponse(
            friendService.getFriendListByUserId(userId = userAuthentication.getUserId())
        )
    }

    @PostMapping
    fun sendFriendRequest(@RequestParam receiverId: Long): CommonResponse<Long> {
        val userAuthentication = SecurityContextHolder.getContext().authentication as UserAuthentication

        return CommonResponse(
            friendService.sendRequest(senderId = userAuthentication.getUserId(), receiverId = receiverId)
        )
    }

    @PostMapping("/{id}/accept")
    fun acceptRequest(@PathVariable id: Long): CommonResponse<Unit> {
        val userAuthentication = SecurityContextHolder.getContext().authentication as UserAuthentication

        return CommonResponse(
            friendService.accept(userId = userAuthentication.getUserId(), id = id)
        )
    }
}