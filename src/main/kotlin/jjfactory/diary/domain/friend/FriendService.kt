package jjfactory.diary.domain.friend

interface FriendService {
    fun sendRequest(senderId: Long, receiverId: Long): Long
    fun deleteFromFriendList(id: Long)
    fun getRequestListByUserId(userId: Long)
    fun getFriendListByUserId(userId: Long)
    fun accept(userId: Long, id: Long)
}