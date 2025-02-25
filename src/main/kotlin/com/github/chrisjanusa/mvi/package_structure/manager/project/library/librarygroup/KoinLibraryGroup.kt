package com.github.chrisjanusa.mvi.package_structure.manager.project.library.librarygroup

import com.github.chrisjanusa.mvi.package_structure.manager.project.library.Library
import com.github.chrisjanusa.mvi.package_structure.manager.project.library.LibraryGroup
import com.github.chrisjanusa.mvi.package_structure.manager.project.library.LibraryManager


fun LibraryManager.addKoin() {
    addLibraryGroup(
        LibraryGroup(
            "koin", "4.0.0", listOf(
                Library(
                    libraryName = "android", libraryModule = "io.insert-koin:koin-android"
                ),
                Library(
                    libraryName = "xcompose", libraryModule = "io.insert-koin:koin-androidx-compose"
                ),
                Library(
                    libraryName = "core", libraryModule = "io.insert-koin:koin-compose"
                ),
                Library(
                    libraryName = "viewmodel", libraryModule = "io.insert-koin:koin-compose-viewmodel"
                ),
            ), listOf(
                Library(
                    libraryName = "testing", libraryModule = "io.insert-koin:koin-test-junit4"
                ),
            ), listOf()
        )
    )
}