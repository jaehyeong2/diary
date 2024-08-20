package jjfactory.diary.domain.report

import jjfactory.diary.TestEntityFactory
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class DiaryReportTest {
    private val testEntityFactory = TestEntityFactory()

    @Test
    fun `본인 게시물을 신고할 수 없다`() {
        val diary = testEntityFactory.ofPublicDiary()

        val report = DiaryReport(
            diary = diary,
            reporterId = diary.userId,
            reason = "테스트"
        )

        assertThatThrownBy {
            report.validate()
        }.isInstanceOf(SelfReportInvalidException::class.java)

    }
}