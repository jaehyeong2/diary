package jjfactory.diary.repository

import jjfactory.diary.domain.diary.Diary
import org.springframework.data.jpa.repository.JpaRepository

interface DiaryRepository : JpaRepository<Diary, Long> {
}