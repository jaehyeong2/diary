package jjfactory.diary.infrastructure.friend

import jjfactory.diary.common.exception.ResourceNotFoundException
import jjfactory.diary.domain.friend.Friend
import jjfactory.diary.domain.friend.FriendReader
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class FriendReaderImpl(
    private val friendRepository: FriendRepository
) : FriendReader {
    override fun get(id: Long): Friend? {
        return friendRepository.findByIdOrNull(id)
    }

    override fun countBySenderIdAndStatusIs(senderId: Long, status: Friend.Status): Long {
        return friendRepository.countBySenderIdAndStatusIs(senderId, status)
    }

    override fun countByReceiverIdAndStatusIs(receiverId: Long, status: Friend.Status): Long {
        return friendRepository.countByReceiverIdAndStatusIs(receiverId, status)
    }

    override fun getFriendListByUserId(userId: Long): List<Friend> {
        return friendRepository.getFriendsByUserId(userId)
    }

    override fun findBySenderIdAndReceiverId(senderId: Long, receiverId: Long): Friend? {
        return friendRepository.findBySenderIdAndReceiverId(senderId, receiverId)
    }

    override fun getOrThrow(id: Long): Friend {
        return friendRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException("friend not found")
    }

    override fun getPage(pageable: Pageable): Page<Friend?> {
        TODO("Not yet implemented")
    }
}