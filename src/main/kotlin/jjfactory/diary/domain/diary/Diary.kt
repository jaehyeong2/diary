package jjfactory.diary.domain.diary

import jakarta.persistence.*
import jjfactory.diary.domain.user.User
import org.hibernate.annotations.CreationTimestamp
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
class Diary(
    @Enumerated(EnumType.STRING)
    var type: Type,
    @Column(columnDefinition="TEXT")
    var content: String,

    val userId: Long,

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

    fun hide(){
        type = Type.PRIVATE
    }

    fun open(){
        type = Type.PUBLIC
    }

    fun modify(type: Type, content: String){
        this.type = type
        this.content = content
    }
}