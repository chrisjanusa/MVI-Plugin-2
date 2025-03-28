package com.github.chrisjanusa.mvi.package_structure.manager.project.library.librarygroup

import com.github.chrisjanusa.mvi.package_structure.manager.project.library.Library
import com.github.chrisjanusa.mvi.package_structure.manager.project.library.LibraryGroup
import com.github.chrisjanusa.mvi.package_structure.manager.project.library.LibraryManager

fun LibraryManager.addCoil() {
    addLibraryGroup(
        LibraryGroup(
            "coil",
            "3.0.0-rc02",
            listOf(
                Library(
                    libraryName = "compose",
                    libraryModule = "io.coil-kt.coil3:coil-compose"
                ),
                Library(
                    libraryName = "core",
                    libraryModule = "io.coil-kt.coil3:coil-compose-core"
                ),
                Library(
                    libraryName = "ktor2",
                    libraryModule = "io.coil-kt.coil3:coil-network-ktor2"
                ),
                Library(
                    libraryName = "ktor3",
                    libraryModule = "io.coil-kt.coil3:coil-network-ktor3"
                ),
            ),
            listOf(),
            listOf()
        )
    )
}