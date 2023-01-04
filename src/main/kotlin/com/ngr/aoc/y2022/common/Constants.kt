package com.ngr.aoc.y2022.common

object Constants {

    private const val DAY_MARKER = "<DAY>"

    private const val PACKAGE = "com.ngr.aoc.y2022.day<DAY>"
    private const val CLASSNAME = "$PACKAGE.Day<DAY>"
    private const val PATH = "/input/2022/"
    private const val FILENAME = "${PATH}input-<DAY>.txt"

    fun filename(day: Int) =
        FILENAME.forDay(day)

    fun className(day: Int) =
        CLASSNAME.forDay(day)

    private fun String.forDay(day: Int) =
        replace(DAY_MARKER, day.toString())
}