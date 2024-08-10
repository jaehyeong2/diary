package jjfactory.diary.repository

import jjfactory.diary.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
}