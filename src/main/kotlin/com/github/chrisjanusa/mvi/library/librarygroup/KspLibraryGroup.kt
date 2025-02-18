package com.github.chrisjanusa.mvi.library.librarygroup

import com.github.chrisjanusa.mvi.library.LibraryGroup
import com.github.chrisjanusa.mvi.library.LibraryManager
import com.github.chrisjanusa.mvi.library.LibraryPlugin

internal fun LibraryManager.addKsp() {
    addLibraryGroup(
        LibraryGroup(
            "ksp",
            "2.0.20-1.0.24",
            listOf(),
            listOf(),
            listOf(
                LibraryPlugin(
                    pluginName = "ksp",
                    pluginId = "com.google.devtools.ksp",
                    apply = false
                )
            )
        )
    )
}