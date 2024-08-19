package jjfactory.diary.domain.diary.comment

import jjfactory.diary.TestEntityFactory
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class CommentTest {
    private val testEntityFactory = TestEntityFactory()

    @Test
    fun `부모 없으면 루트`() {
        val comment = testEntityFactory.ofRootComment()
        assertThat(comment.isRoot()).isTrue
    }

    @Test
    fun `부모 없으면 부모 설정가능`() {
        val comment = testEntityFactory.ofRootComment()
        comment.setParent(50L)
        assertThat(comment.parentId).isEqualTo(50)
    }

    @Test
    fun `부모가 있으면 변경 불가능`() {
        val comment = testEntityFactory.ofRootComment()

        comment.setParent(50L)
        assertThatThrownBy {
            comment.setParent(50L)
        }.isInstanceOf(IllegalArgumentException::class.java)
    }
}