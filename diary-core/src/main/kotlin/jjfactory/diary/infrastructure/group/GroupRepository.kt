package jjfactory.diary.infrastructure.group

import jjfactory.diary.domain.friend.Friend
import jjfactory.diary.domain.group.Group
import org.springframework.data.jpa.repository.JpaRepository

interface GroupRepository: JpaRepository<Group, Long> {
}