package com.github.chrisjanusa.mvi.package_generator.feature

internal data class CreateFeaturePromptResult(
    var featureName: String = "",
    var createSharedState: Boolean = false,
    var createNavGraph: Boolean = false,
)
