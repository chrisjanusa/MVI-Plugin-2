package com.github.chrisjanusa.mvi.package_structure.manager.feature.service.database

import com.github.chrisjanusa.mvi.package_structure.parent_provider.ServiceChild

interface IFeatureDatabaseFileManager: ServiceChild {
    val databasePackage: DatabasePackage
    val databaseWrapperPackage: DatabaseWrapperPackage
    val featureName: String
    val databaseName: String
}