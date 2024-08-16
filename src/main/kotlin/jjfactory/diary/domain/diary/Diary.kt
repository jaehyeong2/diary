package jjfactory.diary.domain.diary

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
class Diary(
    @Enumerated(EnumType.STRING)
    var type: Type,
    @Enumerated(EnumType.STRING)
    var accessLevel: AccessLevel = AccessLevel.PRIVATE,

    @Column(columnDefinition = "TEXT")
    var content: String,

    val userId: Long,

    @CreationTimestamp
    var createdAt: LocalDateTime? = null,
    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    enum class AccessLevel {
        PRIVATE, ALL, GROUP, FRIEND
    }

    enum class Type(msg: String) {
        DAILY("일상"),
        EVENT("특별한 이벤트"),
        ACHIEVEMENTS("성취"),
        INSPIRATION("영감"),
        REFLECTIONS("반성")
    }

    fun hide() {
        accessLevel = AccessLevel.PRIVATE
    }

    fun openToAll() {
        accessLevel = AccessLevel.ALL
    }

    fun modify(type: Type, content: String, accessLevel: AccessLevel) {
        this.type = type
        this.accessLevel = accessLevel
        this.content = content
    }
}