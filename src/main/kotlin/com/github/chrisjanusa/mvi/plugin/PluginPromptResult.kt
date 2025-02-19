package com.github.chrisjanusa.mvi.plugin

data class PluginPromptResult(
    var pluginName: String = "",
    var createSlice: Boolean = false,
    var createState: Boolean = true,
    var createNavDestination: Boolean = true,
)
