package com.github.chrisjanusa.mvi.package_structure.parent_provider

import com.github.chrisjanusa.mvi.package_structure.manager.feature.FeaturePackage

interface FeatureChild: RootChild {
    val featurePackage: FeaturePackage
}