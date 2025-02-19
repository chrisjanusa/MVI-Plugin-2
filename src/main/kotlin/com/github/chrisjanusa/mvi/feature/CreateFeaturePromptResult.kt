package com.github.chrisjanusa.mvi.feature

internal data class CreateFeaturePromptResult(
    var featureName: String = "",
    var createSharedState: Boolean = false,
    var createNavGraph: Boolean = false,
)
