package com.ngr.aoc.y2025.day5

import com.ngr.aoc.common.WithInputHandling
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day5Test : WithInputHandling() {

    private lateinit var day5: Day5

    @BeforeEach
    fun setup() {
        day5 = Day5()
    }

    @Nested
    inner class Part2 {
        @Test
        fun test1() {
            val input = """
                3-5
            """.trimIndent()

            val lines = day5.handleInput(input)

            val output = day5.part2(lines)

            assertThat(output).isEqualTo(3)
        }

        @Test
        fun test2() {
            val input = """
                3-5
                6-9
            """.trimIndent()

            val lines = day5.handleInput(input)

            val output = day5.part2(lines)

            assertThat(output).isEqualTo(7)
        }

        @Test
        fun test3() {
            val input = """
                3-6
                6-9
            """.trimIndent()

            val lines = day5.handleInput(input)

            val output = day5.part2(lines)

            assertThat(output).isEqualTo(7)
        }

        @Test
        fun test4() {
            val input = """
                3-8
                6-9
            """.trimIndent()

            val lines = day5.handleInput(input)

            val output = day5.part2(lines)

            assertThat(output).isEqualTo(7)
        }

        @Test
        fun test5() {
            val input = """
                6-9
                3-8
            """.trimIndent()

            val lines = day5.handleInput(input)

            val output = day5.part2(lines)

            assertThat(output).isEqualTo(7)
        }

        @Test
        fun test6() {
            val input = """
                3-9
                5-8
            """.trimIndent()

            val lines = day5.handleInput(input)

            val output = day5.part2(lines)

            assertThat(output).isEqualTo(7)
        }

        @Test
        fun test7() {
            val input = """
                5-8
                3-9
            """.trimIndent()

            val lines = day5.handleInput(input)

            val output = day5.part2(lines)

            assertThat(output).isEqualTo(7)
        }

        @Test
        fun test9() {
            val input = """
                3-5
                10-14
                16-20
                12-18
            """.trimIndent()

            val lines = day5.handleInput(input)

            val output = day5.part2(lines)

            assertThat(output).isEqualTo(14)
        }

        @Test
        fun test10() {
            val input = """
                3-5
                10-14
                17-22
                12-15
                19-24
                3-4
            """.trimIndent()

            val lines = day5.handleInput(input)

            val output = day5.part2(lines)

            assertThat(output).isEqualTo(17)
        }
    }
}