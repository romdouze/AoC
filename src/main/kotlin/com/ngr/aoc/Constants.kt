package com.ngr.aoc

object Constants {

    private const val DAY_MARKER = "<DAY>"
    private const val YEAR_MARKER = "<YEAR>"

    private const val PACKAGE = "com.ngr.aoc.y<YEAR>.day<DAY>"
    private const val CLASSNAME = "$PACKAGE.Day<DAY>"
    private const val PATH = "/input/<YEAR>/"
    private const val FILENAME = "${PATH}input-<DAY>.txt"

    fun filename(day: Int, year: Int) =
        FILENAME.forDay(day, year)

    fun className(day: Int, year: Int) =
        CLASSNAME.forDay(day, year)

    private fun String.forDay(day: Int, year: Int) =
        replace(DAY_MARKER, day.toString())
            .replace(YEAR_MARKER, year.toString())
}