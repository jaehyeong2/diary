package jjfactory.diary.domain.diary

import jakarta.persistence.*
import jjfactory.diary.domain.user.User
import org.hibernate.annotations.CreationTimestamp
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
class Diary(
    @Enumerated(EnumType.STRING)
    val type: Type,
    @Column(columnDefinition="TEXT")
    val content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    val user: User,

    @CreationTimestamp
    var createdAt: LocalDateTime? = null,
    @LastModifiedDate
    var updatedAt: LocalDateTime? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    enum class Type {
        PRIVATE, PUBLIC
    }
}