package jjfactory.diary.infrastructure

import jjfactory.diary.domain.diary.Diary
import jjfactory.diary.infrastructure.user.UserRepository
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.sql.PreparedStatement
import java.sql.Timestamp
import java.time.LocalDateTime

@RequestMapping("/diary/batch")
@RestController
class DiaryDataImporter(
    private val userRepository: UserRepository,
    private val jdbcTemplate: JdbcTemplate
) {
    @Transactional
    @PostMapping
    fun callBatch(){
        val diaries = mutableListOf<Diary>()

        val user = userRepository.findAll()[1]

        for (i in 1.. 1000000){
            diaries.add(Diary(
                type = Diary.Type.ACHIEVEMENTS,
                Diary.AccessLevel.ALL,
                title = "테스트 $i",
                content = "테스트 $i",
                userId = user.id!!,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
            ))
        }

        insertDiaryEntries(
            entries = diaries,
        )
    }

    fun insertDiaryEntries(entries: List<Diary>) {
        val sql = """
        INSERT INTO public.diary (created_at, updated_at, user_id, access_level, content, type, title)
        VALUES (?, ?, ?, ?, ?, ?, ?)
    """


        jdbcTemplate.batchUpdate(sql, object : BatchPreparedStatementSetter {
            override fun setValues(ps: PreparedStatement, i: Int) {
                val entry = entries[i]
                ps.setTimestamp(1, Timestamp.valueOf(entry.createdAt))
                ps.setTimestamp(2, Timestamp.valueOf(entry.updatedAt))
                ps.setLong(3, entry.userId)
                ps.setString(4, entry.accessLevel.toString())
                ps.setString(5, entry.content)
                ps.setString(6, entry.type.toString())
                ps.setString(7, entry.title)
            }

            override fun getBatchSize(): Int {
                return entries.size
            }
        })
    }
}