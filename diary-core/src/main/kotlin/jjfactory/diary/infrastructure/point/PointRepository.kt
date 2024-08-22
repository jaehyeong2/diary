package jjfactory.diary.infrastructure.point

import jjfactory.diary.domain.point.Point
import org.springframework.data.jpa.repository.JpaRepository

interface PointRepository : JpaRepository<Point, Long> {
}