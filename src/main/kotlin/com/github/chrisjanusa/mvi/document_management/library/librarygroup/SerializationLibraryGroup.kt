package com.github.chrisjanusa.mvi.document_management.library.librarygroup

import com.github.chrisjanusa.mvi.document_management.library.Library
import com.github.chrisjanusa.mvi.document_management.library.LibraryGroup
import com.github.chrisjanusa.mvi.document_management.library.LibraryManager
import com.github.chrisjanusa.mvi.document_management.library.LibraryPlugin

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