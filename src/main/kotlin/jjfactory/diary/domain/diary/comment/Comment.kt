package jjfactory.diary.domain.diary.comment

import jakarta.persistence.*
import jjfactory.diary.domain.diary.Diary
import jjfactory.diary.domain.user.User
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
class Comment(
    @ManyToOne(fetch = FetchType.LAZY)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    val diary: Diary,

    @Column(columnDefinition = "TEXT")
    var content: String,

    @CreationTimestamp
    var createdAt: LocalDateTime? = null,
    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null


    fun modify(content: String) {
        this.content = content
    }
}