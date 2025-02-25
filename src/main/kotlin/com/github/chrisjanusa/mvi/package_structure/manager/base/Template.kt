package com.github.chrisjanusa.mvi.package_structure.manager.base

import com.github.chrisjanusa.mvi.package_structure.Manager
import com.github.chrisjanusa.mvi.package_structure.manager.RootPackage
import com.github.chrisjanusa.mvi.package_structure.manager.feature.FeaturePackage
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.PluginPackage

abstract class Template(
    internal val packageManager: Manager,
    internal val fileName: String
) {
    internal val rootPackage = RootPackage.getFromManager(packageManager)
    internal val rootPackagePath = rootPackage?.packagePath
    internal val featurePackage = FeaturePackage.getFromManager(packageManager)
    internal val featurePackageName = featurePackage?.name
    internal val featureName = featurePackage?.featureName
    internal val pluginPackage = PluginPackage.getFromManager(packageManager)
    internal val pluginPackageName = pluginPackage?.name
    internal val pluginName = pluginPackage?.pluginName ?: ""
    internal val foundationPackage = rootPackage?.foundationPackage
    internal val foundationPackagePath = foundationPackage?.packagePath ?: "error retrieving foundation"

    abstract fun createContent(): String
}