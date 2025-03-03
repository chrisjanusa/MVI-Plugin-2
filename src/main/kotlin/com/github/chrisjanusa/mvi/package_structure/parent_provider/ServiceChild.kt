package com.github.chrisjanusa.mvi.package_structure.parent_provider

import com.github.chrisjanusa.mvi.package_structure.manager.feature.service.ServicePackage

interface ServiceChild: FeatureChild {
    val servicePackage: ServicePackage
}