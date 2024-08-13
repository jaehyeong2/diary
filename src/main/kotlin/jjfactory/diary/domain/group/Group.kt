package jjfactory.diary.domain.group

import jakarta.persistence.*
import jjfactory.diary.domain.user.User
import org.hibernate.annotations.CreationTimestamp
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
class Group(
    var name: String,

    @CreationTimestamp
    var createdAt: LocalDateTime? = null,
    @LastModifiedDate
    var updatedAt: LocalDateTime? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}