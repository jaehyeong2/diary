package jjfactory.diary.infrastructure.point

import jjfactory.diary.domain.point.PointHistory
import org.springframework.data.jpa.repository.JpaRepository

interface PointHistoryRepository: JpaRepository<PointHistory, Long> {
}