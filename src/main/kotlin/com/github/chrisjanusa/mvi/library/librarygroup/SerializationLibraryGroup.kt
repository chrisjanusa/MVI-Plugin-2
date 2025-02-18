package com.github.chrisjanusa.mvi.library.librarygroup

import com.github.chrisjanusa.mvi.library.Library
import com.github.chrisjanusa.mvi.library.LibraryGroup
import com.github.chrisjanusa.mvi.library.LibraryManager
import com.github.chrisjanusa.mvi.library.LibraryPlugin

fun LibraryManager.addSerialization() {
    addPluginLibrary(
        LibraryPlugin(
            pluginName = "serialization",
            pluginId = "org.jetbrains.kotlin.plugin.serialization"
        ),
        "kotlin"
    )
    addLibraryGroup(
        LibraryGroup(
            libraryGroupName = "serialization",
            version = "1.8.0",
            libraries = listOf(
                Library(
                    "json",
                    libraryModule = "org.jetbrains.kotlinx:kotlinx-serialization-json"
                ),
            ),
            listOf(),
            listOf()
        )
    )
}