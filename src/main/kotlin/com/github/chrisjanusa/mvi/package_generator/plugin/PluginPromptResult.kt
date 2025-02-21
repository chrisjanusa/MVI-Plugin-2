package com.github.chrisjanusa.mvi.package_generator.plugin

data class PluginPromptResult(
    var pluginName: String = "",
    var createSlice: Boolean = false,
    var createState: Boolean = true,
    var createNavDestination: Boolean = true,
)
