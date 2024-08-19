package jjfactory.diary.domain.diary.comment

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
class Comment(
    val userId: Long,
    val diaryId: Long,
    var parentId: Long? = null,

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

    fun isRoot(): Boolean {
        return parentId == null
    }

    fun isChild(): Boolean {
        return parentId != null
    }

    fun setParent(parentId: Long) {
        if (this.parentId != null) {
            throw IllegalArgumentException()
        }
        this.parentId = parentId

    }

    fun modify(content: String, parentId: Long?) {
        this.content = content
        parentId?.let { setParent(parentId) }
    }
}