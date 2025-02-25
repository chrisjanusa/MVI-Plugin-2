package com.github.chrisjanusa.mvi.package_structure.manager.project.library.librarygroup

import com.github.chrisjanusa.mvi.package_structure.manager.project.library.Library
import com.github.chrisjanusa.mvi.package_structure.manager.project.library.LibraryGroup
import com.github.chrisjanusa.mvi.package_structure.manager.project.library.LibraryManager

fun LibraryManager.addKtor() {
    addLibraryGroup(
        LibraryGroup(
            "ktor",
            "3.0.0",
            listOf(
                Library(
                    libraryName = "android",
                    libraryModule = "io.ktor:ktor-client-android"
                ),
                Library(
                    libraryName = "okhttp",
                    libraryModule = "io.ktor:ktor-client-okhttp"
                ),
                Library(
                    libraryName = "core",
                    libraryModule = "io.ktor:ktor-client-core"
                ),
                Library(
                    libraryName = "serialization",
                    libraryModule = "io.ktor:ktor-client-serialization-jvm"
                ),
                Library(
                    libraryName = "logging",
                    libraryModule = "io.ktor:ktor-client-logging"
                ),
                Library(
                    libraryName = "json",
                    libraryModule = "io.ktor:ktor-serialization-kotlinx-json"
                ),
                Library(
                    libraryName = "negotiation",
                    libraryModule = "io.ktor:ktor-client-content-negotiation"
                ),
            ),
            listOf(),
            listOf()
        )
    )
}