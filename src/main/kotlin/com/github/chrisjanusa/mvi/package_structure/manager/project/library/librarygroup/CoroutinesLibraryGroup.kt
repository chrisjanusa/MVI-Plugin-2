package com.github.chrisjanusa.mvi.package_structure.manager.project.library.librarygroup

import com.github.chrisjanusa.mvi.package_structure.manager.project.library.Library
import com.github.chrisjanusa.mvi.package_structure.manager.project.library.LibraryGroup
import com.github.chrisjanusa.mvi.package_structure.manager.project.library.LibraryManager

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