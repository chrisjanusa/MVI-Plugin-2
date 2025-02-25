package com.github.chrisjanusa.mvi.package_structure.manager.project.library.librarygroup

import com.github.chrisjanusa.mvi.package_structure.manager.project.library.LibraryGroup
import com.github.chrisjanusa.mvi.package_structure.manager.project.library.LibraryManager
import com.github.chrisjanusa.mvi.package_structure.manager.project.library.LibraryPlugin

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