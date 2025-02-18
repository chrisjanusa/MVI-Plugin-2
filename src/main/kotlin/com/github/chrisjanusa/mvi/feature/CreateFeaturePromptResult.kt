package com.github.chrisjanusa.mvi.feature

data class CreateFeaturePromptResult(
    var featureName: String = "",
    var createSharedState: Boolean = false,
    var createNavGraph: Boolean = false,
)
