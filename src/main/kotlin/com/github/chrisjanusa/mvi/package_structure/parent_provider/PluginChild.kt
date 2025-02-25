package com.github.chrisjanusa.mvi.package_structure.parent_provider

import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.PluginPackage

interface PluginChild : FeatureChild {
    val pluginPackage: PluginPackage
}