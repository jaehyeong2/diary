package jjfactory.diary.infrastructure.point

import jjfactory.diary.domain.point.Transactions
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionsRepository: JpaRepository<Transactions, Long> {
}