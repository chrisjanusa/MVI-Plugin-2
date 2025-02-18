package com.github.chrisjanusa.mvi.library.librarygroup

import com.github.chrisjanusa.mvi.library.Library
import com.github.chrisjanusa.mvi.library.LibraryGroup
import com.github.chrisjanusa.mvi.library.LibraryManager

fun LibraryManager.addRoom() {
    addLibraryGroup(
        LibraryGroup(
            "room",
            "2.6.1",
            listOf(
                Library(
                    libraryName = "runtime",
                    libraryModule = "androidx.room:room-runtime"
                ),
                Library(
                    libraryName = "ktx",
                    libraryModule = "androidx.room:room-ktx"
                ),
            ),
            listOf(
                Library(
                    libraryName = "testing",
                    libraryModule = "androidx.room:room-testing"
                ),
            ),
            listOf()
        )
    )
    addKsp()
    addLibrary(
        library = Library(
            libraryName = "compiler",
            libraryModule = "androidx.room:room-compiler"
        ),
        libraryGroupName = "room",
        isKsp = true
    )
}