package com.github.chrisjanusa.mvi.package_structure.manager.project.library.librarygroup

import com.github.chrisjanusa.mvi.package_structure.manager.project.library.Library
import com.github.chrisjanusa.mvi.package_structure.manager.project.library.LibraryGroup
import com.github.chrisjanusa.mvi.package_structure.manager.project.library.LibraryManager

fun LibraryManager.addNavigation() {
    addLibraryGroup(
        LibraryGroup(
            "navigation",
            "2.8.7",
            listOf(
                Library(
                    libraryName = "compose",
                    libraryModule = "androidx.navigation:navigation-compose"
                ),
            ),
            listOf(
                Library(
                    libraryName = "testing",
                    libraryModule = "androidx.navigation:navigation-testing"
                ),
            ),
            listOf()
        )
    )
}