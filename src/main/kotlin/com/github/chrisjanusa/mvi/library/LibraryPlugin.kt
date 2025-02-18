package com.github.chrisjanusa.mvi.library

data class LibraryPlugin(
    val pluginName: String,
    val pluginId: String,
    val apply: Boolean = true
)