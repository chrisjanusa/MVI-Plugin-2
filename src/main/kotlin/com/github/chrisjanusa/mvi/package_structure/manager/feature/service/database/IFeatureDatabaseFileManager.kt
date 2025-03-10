package com.github.chrisjanusa.mvi.package_structure.manager.feature.service.database

import com.github.chrisjanusa.mvi.package_structure.parent_provider.DatabaseChild

interface IFeatureDatabaseFileManager: DatabaseChild {
    val databaseWrapperPackage: DatabaseWrapperPackage
    val featureName: String
    val databaseName: String
}