package com.github.chrisjanusa.mvi.action.feature.service.database

internal data class DatabasePromptResult(
    var name: String = "",
    var entityNames: MutableList<String> = mutableListOf()
)
