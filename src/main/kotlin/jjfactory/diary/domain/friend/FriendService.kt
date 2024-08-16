package jjfactory.diary.domain.friend

import jjfactory.diary.domain.user.UserInfo

interface FriendService {
    fun sendRequest(senderId: Long, receiverId: Long): Long
    fun deleteFromFriendList(id: Long)
    fun getRequestListByUserId(userId: Long)
    fun getFriendListByUserId(userId: Long): List<UserInfo.Detail>
    fun accept(userId: Long, id: Long)
}