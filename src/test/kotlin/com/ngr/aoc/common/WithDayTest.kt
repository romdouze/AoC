package com.ngr.aoc.common

import com.ngr.aoc.Day
import org.junit.jupiter.api.BeforeEach
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

abstract class WithDayTest<T : Day<*, *, *>>(private val klass: KClass<T>) {
    protected lateinit var day: T

    @BeforeEach
    fun setup() {
        day = klass.createInstance()
    }
}