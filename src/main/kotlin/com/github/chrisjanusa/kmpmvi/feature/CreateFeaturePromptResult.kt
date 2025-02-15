package com.github.chrisjanusa.kmpmvi.feature

data class CreateFeaturePromptResult(
    var featureName: String = "",
    var createServiceModule: Boolean = false,
    var createSharedViewModel: Boolean = false,
    var createDomainModelModule: Boolean = false,
    var createNavGraph: Boolean = false,
)
