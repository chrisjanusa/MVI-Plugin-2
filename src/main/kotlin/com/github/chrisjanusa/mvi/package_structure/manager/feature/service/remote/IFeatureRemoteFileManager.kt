package com.github.chrisjanusa.mvi.package_structure.manager.feature.service.remote

import com.github.chrisjanusa.mvi.package_structure.parent_provider.ServiceChild

interface IFeatureRemoteFileManager: ServiceChild {
    val remotePackage: FeatureRemotePackage
    val remoteWrapperPackage: FeatureRemoteWrapperPackage
    val featureName: String
    val dataSourceName: String
}