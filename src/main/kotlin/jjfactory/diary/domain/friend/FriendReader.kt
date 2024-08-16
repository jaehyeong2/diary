package jjfactory.diary.domain.friend

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface FriendReader {
    fun get(id: Long): Friend?
    fun getOrThrow(id: Long): Friend
    fun findBySenderIdAndReceiverId(senderId: Long, receiverId: Long): Friend?
    fun getPage(pageable: Pageable): Page<Friend?>
    fun countBySenderIdAndStatusIs(senderId: Long, status: Friend.Status): Long
    fun countByReceiverIdAndStatusIs(receiverId: Long, status: Friend.Status): Long
    fun getFriendListByUserId(userId: Long): List<Friend>
    fun getRequestListByUserId(userId: Long): List<Friend>
}