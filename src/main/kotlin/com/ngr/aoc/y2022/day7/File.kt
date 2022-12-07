package com.ngr.aoc.y2022.day7

open class File(
    val name: String,
    val parent: Dir?,
    open val size: Int,
) {
    companion object {
        fun fromShellEntry(shellEntry: ShellEntry, currentDir: Dir): File =
            when (shellEntry) {
                is FileEntry -> File(shellEntry.name, currentDir, shellEntry.size)
                is DirEntry -> Dir(shellEntry.name, currentDir)
                is CommandEntry -> throw IllegalArgumentException("Cannot create File from CommandEntry: $shellEntry")
            }
    }
}