package jjfactory.diary.infrastructure.diary

import jjfactory.diary.domain.diary.Diary
import jjfactory.diary.domain.diary.DiaryReader
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest

@DataJpaTest
class DiaryReaderImplTest {
    @Autowired
    lateinit var diaryRepository: DiaryRepository

    @Autowired
    lateinit var diaryReader: DiaryReader

    @Test
    fun getDiaryPage() {
        val diary = Diary(
            type = Diary.Type.DAILY,
            content = "test",
            userId = 2L
        )

        diaryRepository.save(diary)

        val req = PageRequest.of(0, 10)


        diaryReader.getDiaryPage(req).forEach {
            println("it = ${it}")
        }
    }
}