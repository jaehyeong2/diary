package jjfactory.diary.domain.diary

import jakarta.persistence.Column
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import jjfactory.diary.domain.user.User
import org.hibernate.annotations.CreationTimestamp
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

class DiaryInfo {
    data class Detail(
        val id: Long,
        var type: Diary.Type,
        var content: String,
        val userId: Long,
        val username: String,
        var createdAt: LocalDateTime? = null,
        var updatedAt: LocalDateTime? = null,
    )
}