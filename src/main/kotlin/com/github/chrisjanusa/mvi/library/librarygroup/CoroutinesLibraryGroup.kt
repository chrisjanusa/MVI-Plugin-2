package com.github.chrisjanusa.mvi.library.librarygroup

import com.github.chrisjanusa.mvi.library.Library
import com.github.chrisjanusa.mvi.library.LibraryGroup
import com.github.chrisjanusa.mvi.library.LibraryManager

fun LibraryManager.addCoroutines() {
    addLibraryGroup(
        LibraryGroup(
            "coroutines",
            "1.9.0",
            listOf(
                Library(
                    libraryName = "android",
                    libraryModule = "org.jetbrains.kotlinx:kotlinx-coroutines-android"
                ),
            ),
            listOf(),
            listOf()
        )
    )
}