package com.ngr.aoc.y2025.day1

import com.ngr.aoc.common.WithDayTest
import com.ngr.aoc.common.WithInputHandling
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day1Test : WithInputHandling, WithDayTest<Day1>(Day1::class) {

    @Nested
    inner class Part2 {
        @Test
        fun test1() {
            val input = """
            R50
            R250
        """.trimIndent()

            val lines = day.handleInput(input)

            val output = day.part2(lines)

            assertThat(output).isEqualTo(3)
        }

        @Test
        fun test2() {
            val input = """
            R50
            R200
        """.trimIndent()

            val lines = day.handleInput(input)

            val output = day.part2(lines)

            assertThat(output).isEqualTo(3)
        }

        @Test
        fun test3() {
            val input = """
            R50
            L200
        """.trimIndent()

            val lines = day.handleInput(input)

            val output = day.part2(lines)

            assertThat(output).isEqualTo(3)
        }

        @Test
        fun test4() {
            val input = """
            R50
            L100
        """.trimIndent()

            val lines = day.handleInput(input)

            val output = day.part2(lines)

            assertThat(output).isEqualTo(2)
        }

        @Test
        fun test5() {
            val input = """
            R50
            R100
        """.trimIndent()

            val lines = day.handleInput(input)

            val output = day.part2(lines)

            assertThat(output).isEqualTo(2)
        }

        @Test
        fun test6() {
            val input = """
            R50
        """.trimIndent()

            val lines = day.handleInput(input)

            val output = day.part2(lines)

            assertThat(output).isEqualTo(1)
        }

        @Test
        fun test7() {
            val input = """
            R49
            R1
        """.trimIndent()

            val lines = day.handleInput(input)

            val output = day.part2(lines)

            assertThat(output).isEqualTo(1)
        }

        @Test
        fun test8() {
            val input = """
            R49
        """.trimIndent()

            val lines = day.handleInput(input)

            val output = day.part2(lines)

            assertThat(output).isEqualTo(0)
        }

        @Test
        fun test9() {
            val input = """
            L50
        """.trimIndent()

            val lines = day.handleInput(input)

            val output = day.part2(lines)

            assertThat(output).isEqualTo(1)
        }

        @Test
        fun test10() {
            val input = """
            L51
        """.trimIndent()

            val lines = day.handleInput(input)

            val output = day.part2(lines)

            assertThat(output).isEqualTo(1)
        }

        @Test
        fun test11() {
            val input = """
            L168
            R418
        """.trimIndent()

            val lines = day.handleInput(input)

            val output = day.part2(lines)

            assertThat(output).isEqualTo(7)
        }

        @Test
        fun test12() {
            val input = """
            L168
            R118
        """.trimIndent()

            val lines = day.handleInput(input)

            val output = day.part2(lines)

            assertThat(output).isEqualTo(4)
        }

        @Test
        fun test13() {
            val input = """
            L168
            R18
        """.trimIndent()

            val lines = day.handleInput(input)

            val output = day.part2(lines)

            assertThat(output).isEqualTo(3)
        }

        @Test
        fun test14() {
            val input = """
            L168
            R19
        """.trimIndent()

            val lines = day.handleInput(input)

            val output = day.part2(lines)

            assertThat(output).isEqualTo(3)
        }

        @Test
        fun test15() {
            val input = """
            L168
        """.trimIndent()

            val lines = day.handleInput(input)

            val output = day.part2(lines)

            assertThat(output).isEqualTo(2)
        }

        @Test
        fun test16() {
            val input = """
            L68
        """.trimIndent()

            val lines = day.handleInput(input)

            val output = day.part2(lines)

            assertThat(output).isEqualTo(1)
        }

        @Test
        fun test17() {
            val input = """
            L68
            L30
            R48
            L5
            R60
            L55
            L1
            L99
            R14
            L82
        """.trimIndent()

            val lines = day.handleInput(input)

            val output = day.part2(lines)

            assertThat(output).isEqualTo(6)
        }
    }
}