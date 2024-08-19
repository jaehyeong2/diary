package jjfactory.diary.domain.friend

import jjfactory.diary.common.exception.DuplicateRequestException
import jjfactory.diary.domain.user.UserInfo
import jjfactory.diary.domain.user.UserReader
import jjfactory.diary.infrastructure.friend.FriendRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class FriendServiceImpl(
    private val friendRepository: FriendRepository,
    private val friendReader: FriendReader,
    private val userReader: UserReader
) : FriendService {

    private val FRIEND_LIMIT = 20

    //친구 20명까지
    //fixme 친구 목록에 쌍방 같은 사람들어가면 한 로우로 되어야하는게 아니니 재형아
    override fun sendRequest(senderId: Long, receiverId: Long): Long {
        if (senderId == receiverId) throw IllegalArgumentException()

        friendReader.findBySenderIdAndReceiverId(senderId, receiverId)?.let {
            throw DuplicateRequestException()
        }

        if (friendReader.countBySenderIdAndStatusIs(senderId, status = Friend.Status.ACCEPTED) >= FRIEND_LIMIT) {
            throw IllegalStateException("나의 친구 목록이 다 찼습니다.")
        }

        if (friendReader.countByReceiverIdAndStatusIs(receiverId, status = Friend.Status.ACCEPTED) >= FRIEND_LIMIT) {
            throw IllegalStateException("상대의 친구 목록이 다 찼습니다.")
        }

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
    override fun getRequestListByUserId(userId: Long, accepted: Boolean): List<UserInfo.Detail> {
        val friendIds = friendReader.getRequestListByUserId(userId).map {
            if (accepted) it.senderId
            else it.receiverId
        }

        return userReader.getByIdIn(friendIds).map {
            UserInfo.Detail(
                id = it.id!!,
                lastName = it.lastName,
                firstName = it.firstName,
                phone = it.phone,
                gender = it.gender.toString(),
                email = it.email,
                username = it.username,
                point = it.point
            )
        }
    }

    //todo 성능 안좋으면 개선
    @Cacheable(cacheNames = ["friend_list"], key = "#userId")
    @Transactional(readOnly = true)
    override fun getFriendListByUserId(userId: Long): List<UserInfo.Detail> {
        val friendIds = friendReader.getFriendListByUserId(userId).map {
            val friendId = if (userId == it.senderId) it.senderId
            else it.receiverId

            friendId
        }

        return userReader.getByIdIn(friendIds).map {
            UserInfo.Detail(
                id = it.id!!,
                lastName = it.lastName,
                firstName = it.firstName,
                phone = it.phone,
                gender = it.gender.toString(),
                email = it.email,
                username = it.username,
                point = it.point
            )
        }
    }

    @CacheEvict(cacheNames = ["friend_list"], key = "#userId")
    override fun accept(userId: Long, id: Long) {
        val friend = friendReader.getOrThrow(id)
        friend.accept(userId)
    }
}