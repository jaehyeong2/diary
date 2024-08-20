package jjfactory.diary.infrastructure.report

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import jjfactory.diary.domain.report.DiaryReport
import org.springframework.data.jpa.repository.JpaRepository

interface DiaryReportRepository: JpaRepository<DiaryReport, Long>, KotlinJdslJpqlExecutor {
    fun findByDiaryIdAndReporterId(diaryId: Long, reporterId: Long): DiaryReport?
}