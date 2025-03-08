package com.github.chrisjanusa.mvi.package_structure.manager.feature.service.database

import com.github.chrisjanusa.mvi.package_structure.manager.feature.service.FeatureServicePackage
import com.intellij.openapi.vfs.VirtualFile

class FeatureDatabaseFileManager(file: VirtualFile): IFeatureDatabaseFileManager {
    override val databasePackage by lazy {
        DatabasePackage(file.parent)
    }
    override val databaseName by lazy {
        databasePackage.databaseName
    }
    override val databaseWrapperPackage by lazy {
        databasePackage.databaseWrapperPackage
    }
    override val servicePackage: FeatureServicePackage by lazy {
        databasePackage.servicePackage
    }

    override val featurePackage by lazy {
        servicePackage.featurePackage
    }
    override val featureName by lazy {
        servicePackage.featureName
    }
    override val rootPackage by lazy {
        servicePackage.rootPackage
    }
}