package jjfactory.diary.domain.friend

import jjfactory.diary.common.exception.AccessForbiddenException
import jjfactory.diary.infrastructure.friend.FriendRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class FriendServiceImpl(
    private val friendRepository: FriendRepository,
    private val friendReader: FriendReader
) : FriendService {
    override fun sendRequest(senderId: Long, receiverId: Long): Long {
        val initFriend = Friend(
            senderId = senderId,
            receiverId = receiverId,
        )

        return friendRepository.save(initFriend).id!!
    }

    override fun deleteFromFriendList(id: Long) {
        friendReader.get(id)?.let {
            friendRepository.delete(it)
        }
    }

    @Transactional(readOnly = true)
    override fun getRequestListByUserId(userId: Long) {
        TODO("Not yet implemented")
    }

    @Transactional(readOnly = true)
    override fun getFriendListByUserId(userId: Long) {
        TODO("Not yet implemented")
    }

    override fun accept(userId: Long, id: Long) {
        val friend = friendReader.getOrThrow(id)
        friend.accept(userId)
    }
}