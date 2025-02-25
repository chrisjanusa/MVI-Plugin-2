package com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.helper
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.generated.PluginGeneratedPackage
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.ui.PluginUiPackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.PluginChild

interface IPluginFileManager: PluginChild {
    val generatedPackage: PluginGeneratedPackage?
    val pluginName: String
    val uiPackage: PluginUiPackage?
    fun addSlice()
    fun removeSlice()
    fun addState()
    fun removeState()
}