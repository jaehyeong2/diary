package jjfactory.diary.domain.notification

import org.springframework.data.domain.Page

interface NotificationReader {
    fun get(id: Long): Notification?
    fun getOrThrow(id: Long): Notification
    fun getPageByTargetUserId(userId: Long): Page<Notification>
    fun findAllByTargetUserIdAndReadIsFalse(userId: Long): List<Notification>
}