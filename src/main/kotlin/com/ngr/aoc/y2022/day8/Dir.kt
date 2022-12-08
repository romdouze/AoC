package com.ngr.aoc.y2022.day8

import java.awt.Point

enum class Dir(
    val dx: Int,
    val dy: Int,
    val slices: (List<List<Int>>) -> List<List<Pair<Int, Point>>>,
) {
    N(0,
        1,
        { heights ->
            heights.widthRange().map { x ->
                heights.heightRange()
                    .map { heights[it][x] to Point(x, it) }
            }
        }),
    S(0,
        -1,
        { heights ->
            heights.widthRange().map { x ->
                heights.heightRange()
                    .reversed()
                    .map { heights[it][x] to Point(x, it) }
            }
        }),
    E(1,
        0,
        { heights ->
            heights.heightRange().map { y ->
                heights.widthRange()
                    .map { heights[y][it] to Point(it, y) }
            }
        }),
    W(-1,
        0,
        { heights ->
            heights.heightRange().map { y ->
                heights.widthRange()
                    .reversed()
                    .map { heights[y][it] to Point(it, y) }
            }
        })
}