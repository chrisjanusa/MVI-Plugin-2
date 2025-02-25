package com.github.chrisjanusa.mvi.package_structure.manager.project.library

data class LibraryPlugin(
    val pluginName: String,
    val pluginId: String,
    val apply: Boolean = true
)