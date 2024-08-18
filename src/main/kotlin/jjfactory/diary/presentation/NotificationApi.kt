package jjfactory.diary.presentation

import jjfactory.diary.common.response.CommonPagingResponse
import jjfactory.diary.common.response.PagingResponse
import jjfactory.diary.config.security.AuthSupporter
import jjfactory.diary.domain.notification.NotificationInfo
import jjfactory.diary.domain.notification.NotificationService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/notifications")
@RestController
class NotificationApi(
    private val notificationService: NotificationService
) {

    @GetMapping("/my")
    fun getNotifications(@PageableDefault pageable: Pageable, @RequestParam(required = false) isRead: Boolean?): CommonPagingResponse<NotificationInfo.List> {
        val userId = AuthSupporter.getLoginUserId()

        val result = notificationService.getAllNotificationsByTargetId(
            targetUserId = userId,
            pageable = pageable,
            isRead = isRead
        )

        return CommonPagingResponse(PagingResponse(result))
    }
}