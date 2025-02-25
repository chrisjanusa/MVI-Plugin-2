package com.github.chrisjanusa.mvi.package_structure.manager.feature.service.database

import com.github.chrisjanusa.mvi.package_structure.manager.feature.service.ServicePackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.FeatureChild

interface IDatabaseServiceFileManager: FeatureChild {
    val databasePackage: FeatureDatabasePackage
    val servicePackage: ServicePackage
    val featureName: String
}