package com.github.chrisjanusa.mvi.package_structure.parent_provider

import com.github.chrisjanusa.mvi.package_structure.manager.feature.service.FeatureServicePackage

interface ServiceChild: FeatureChild {
    val servicePackage: FeatureServicePackage
}