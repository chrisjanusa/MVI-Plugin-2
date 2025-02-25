package com.github.chrisjanusa.mvi.action.feature

internal data class CreateFeaturePromptResult(
    var featureName: String = "",
    var createSharedState: Boolean = false,
    var createNavGraph: Boolean = false,
)
