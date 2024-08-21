package jjfactory.diary.domain.report

class ReportInfo {
    data class Detail(
        val id: Long,
        val reporterId: Long,
        val reporterName: String,
        val content: String
    )
}