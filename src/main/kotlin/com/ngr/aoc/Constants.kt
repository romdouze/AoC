package com.ngr.aoc

object Constants {

    private const val DAY_MARKER = "<DAY>"
    private const val YEAR_MARKER = "<YEAR>"

    private const val PACKAGE = "com.ngr.aoc.y$YEAR_MARKER.day$DAY_MARKER"
    private const val CLASSNAME = "$PACKAGE.Day$DAY_MARKER"
    private const val PATH = "/input/$YEAR_MARKER/"
    private const val FILENAME = "${PATH}input-$DAY_MARKER.txt"

    fun filename(day: Int, year: Int) =
        FILENAME.forDay(day, year)

    fun className(day: Int, year: Int) =
        CLASSNAME.forDay(day, year)

    private fun String.forDay(day: Int, year: Int) =
        replace(DAY_MARKER, day.toString())
            .replace(YEAR_MARKER, year.toString())
}