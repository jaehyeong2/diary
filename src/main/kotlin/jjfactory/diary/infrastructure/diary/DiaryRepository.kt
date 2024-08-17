package jjfactory.diary.infrastructure.diary

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import jjfactory.diary.domain.diary.Diary
import org.springframework.data.jpa.repository.JpaRepository

interface DiaryRepository : JpaRepository<Diary, Long>, KotlinJdslJpqlExecutor {
    fun findAllByAccessLevelIsOrderByCreatedAtDesc(accessLevel: Diary.AccessLevel): List<Diary>
}