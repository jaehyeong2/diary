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

    @Enumerated(EnumType.STRING)
    var status: Status = Status.PENDING,

    @CreationTimestamp
    var createdAt: LocalDateTime? = null,
    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    enum class Status {
        PENDING, REVIEWED, BLOCKED
    }

    fun check() {
        if (status == Status.PENDING) status = Status.REVIEWED
    }

    fun block(){
        status = Status.BLOCKED
    }

    fun validate() {
        if (diary.userId == reporterId)
            throw SelfReportInvalidException()
    }
}