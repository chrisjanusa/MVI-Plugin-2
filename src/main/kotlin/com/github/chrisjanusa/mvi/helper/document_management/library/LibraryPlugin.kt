package com.github.chrisjanusa.mvi.helper.document_management.library

data class LibraryPlugin(
    val pluginName: String,
    val pluginId: String,
    val apply: Boolean = true
)