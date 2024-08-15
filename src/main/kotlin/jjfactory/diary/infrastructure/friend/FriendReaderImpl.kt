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

    override fun getOrThrow(id: Long): Friend {
        return friendRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException("friend not found")
    }

    override fun getPage(pageable: Pageable): Page<Friend?> {
        TODO("Not yet implemented")
    }
}