package jjfactory.diary.infrastructure

import jjfactory.diary.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
}