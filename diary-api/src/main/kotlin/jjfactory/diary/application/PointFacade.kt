package jjfactory.diary.application

import jjfactory.diary.domain.notification.NotificationService
import jjfactory.diary.domain.notification.NotificationType
import jjfactory.diary.domain.user.PointService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class PointFacade(
    private val pointService: PointService,
    private val notificationService: NotificationService
) {

    @Transactional
    fun sendPoint(sourceUserId: Long, targetUserId: Long, point: Int){
        pointService.send(
            sourceUserId = sourceUserId,
            targetUserId = targetUserId,
            point = point
        )

        notificationService.store(
            sourceUserId = sourceUserId,
            targetUserId = targetUserId,
            type = NotificationType.POINT_RECEIVED
        )
    }
}