package jjfactory.diary.infrastructure.diary.report

import jjfactory.diary.domain.diary.report.DiaryReport
import org.springframework.data.jpa.repository.JpaRepository

interface DiaryReportRepository: JpaRepository<DiaryReport, Long> {
    fun findByDiaryIdAndReporterId(diaryId: Long, reporterId: Long): DiaryReport?
}