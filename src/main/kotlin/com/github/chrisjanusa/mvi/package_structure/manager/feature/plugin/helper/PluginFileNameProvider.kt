package com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.helper

import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticSuffixChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.generated.PluginGeneratedPackage
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.PluginPackage

abstract class PluginFileNameProvider(suffix: String) : StaticSuffixChildInstanceCompanion(
    suffix = suffix,
    PluginPackage,
    PluginGeneratedPackage
) {
    fun getFileName(pluginName: String): String = "$pluginName$SUFFIX"
}