package jjfactory.diary.infrastructure.user

import jjfactory.diary.domain.diary.Diary
import org.springframework.data.jpa.repository.JpaRepository

interface DiaryRepository : JpaRepository<Diary, Long> {
}