package com.github.chrisjanusa.mvi.helper.document_management.library.librarygroup

import com.github.chrisjanusa.mvi.helper.document_management.library.Library
import com.github.chrisjanusa.mvi.helper.document_management.library.LibraryGroup
import com.github.chrisjanusa.mvi.helper.document_management.library.LibraryManager

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