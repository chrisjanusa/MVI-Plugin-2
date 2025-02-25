package com.github.chrisjanusa.mvi.package_structure.manager.feature.service.database

import com.github.chrisjanusa.mvi.package_structure.manager.feature.service.ServicePackage
import com.intellij.openapi.vfs.VirtualFile

abstract class DatabaseServiceFileManager(file: VirtualFile): IDatabaseServiceFileManager {
    override val databasePackage by lazy {
        FeatureDatabasePackage(file)
    }
    override val servicePackage: ServicePackage by lazy {
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