package jjfactory.diary.infrastructure.friend

import jjfactory.diary.domain.friend.Friend
import org.springframework.data.jpa.repository.JpaRepository

interface FriendRepository: JpaRepository<Friend, Long> {
}