package jjfactory.diary.domain.notification

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface NotificationReader {
    fun get(id: Long): Notification?
    fun getOrThrow(id: Long): Notification
    fun findAllByTargetUserIdAndReadIsFalse(userId: Long): List<Notification>
    fun getPageByTargetUserId(userId: Long, pageable: Pageable, isRead: Boolean?): Page<Notification?>
}