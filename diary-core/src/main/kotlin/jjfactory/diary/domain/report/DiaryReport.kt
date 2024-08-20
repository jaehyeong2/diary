package jjfactory.diary.domain.report

import jakarta.persistence.*
import jjfactory.diary.domain.diary.Diary
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
class DiaryReport(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    val diary: Diary,
    val reporterId: Long,
    val reason: String,

    var checked: Boolean = false,

    @CreationTimestamp
    var createdAt: LocalDateTime? = null,
    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun read(){
        checked = true
    }

    fun validate(){
        if (diary.userId == reporterId)
            throw SelfReportInvalidException()
    }
}