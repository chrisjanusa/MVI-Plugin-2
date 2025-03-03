package com.github.chrisjanusa.mvi.package_structure.manager.feature.service.remote

import com.github.chrisjanusa.mvi.package_structure.manager.feature.service.ServicePackage
import com.intellij.openapi.vfs.VirtualFile

class FeatureRemoteFileManager(file: VirtualFile): IFeatureRemoteFileManager {
    override val remotePackage by lazy {
        FeatureRemotePackage(file.parent)
    }
    override val dataSourceName by lazy {
        remotePackage.dataSourceName
    }
    override val remoteWrapperPackage by lazy {
        remotePackage.remoteWrapperPackage
    }
    override val servicePackage: ServicePackage by lazy {
        remotePackage.servicePackage
    }

    override val featurePackage by lazy {
        remotePackage.featurePackage
    }
    override val featureName by lazy {
        remotePackage.featureName
    }
    override val rootPackage by lazy {
        remotePackage.rootPackage
    }
}