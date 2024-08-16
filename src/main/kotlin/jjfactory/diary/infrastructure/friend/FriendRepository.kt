package jjfactory.diary.infrastructure.friend

import jjfactory.diary.domain.friend.Friend
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface FriendRepository: JpaRepository<Friend, Long> {
    fun findBySenderIdAndReceiverId(senderId: Long, receiverId: Long): Friend?
    fun countByReceiverId(receiverId: Long): Long
    fun countBySenderIdAndStatusIs(senderId: Long, status: Friend.Status): Long
    fun countByReceiverIdAndStatusIs(receiverId: Long, status: Friend.Status): Long

    @Query(nativeQuery = true, value = "select * from friend f where (f.sender_id = ?1 or f.receiver_id = ?1) and status = ?2 order by f.updated_at desc")
    fun getFriendsByUserId(userId: Long, status: String): List<Friend>

}