package com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin

import com.github.chrisjanusa.mvi.package_structure.manager.base.EffectFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.helper.IPluginFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.helper.PluginFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.helper.PluginFileNameProvider
import com.github.chrisjanusa.mvi.package_structure.manager.foundation.FoundationSliceFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.foundation.FoundationStateFileManager
import com.intellij.openapi.vfs.VirtualFile

class PluginEffectFileManager(
    file: VirtualFile,
    pluginFileManager: PluginFileManager = PluginFileManager(file)
) : EffectFileManager(file), IPluginFileManager by pluginFileManager {
    override val baseName: String by lazy {
        pluginName
    }
    override val actionName = PluginActionFileManager.getFileName(pluginName)
    override val typeAliasPath: String by lazy {
        generatedPackage?.packagePath ?: ""
    }
    override val fileName: String by lazy {
        getFileName(pluginName)
    }
    override val actionEffect: String by lazy {
        getActionName(pluginName)
    }
    override val stateEffect: String by lazy {
        getStateName(pluginName)
    }
    override val stateSliceEffect: String by lazy {
        getStateSliceName(pluginName)
    }
    override val navEffect: String by lazy {
        getNavName(pluginName)
    }
    override val hasState by lazy {
        pluginPackage.hasState
    }
    override val hasSlice by lazy {
        pluginPackage.hasSlice
    }
    override val state by lazy {
        if (pluginPackage.hasState) PluginStateFileManager.getFileName(pluginName) else FoundationStateFileManager.NO_STATE
    }
    override val slice by lazy {
        if (pluginPackage.hasSlice) PluginSliceFileManager.getFileName(pluginName) else FoundationSliceFileManager.NO_SLICE
    }

    override fun getEffectFullName(effectName: String) = "${effectName}Effect"

    override fun addEffectToViewModel(effectName: String) {
        pluginPackage.viewModel?.addEffect(effectName, "${pluginPackage.pluginEffect?.packagePathExcludingFile}.$effectName${EffectFileManager.SUFFIX}")
    }


    companion object : PluginFileNameProvider(SUFFIX) {
        override fun createInstance(virtualFile: VirtualFile) = PluginEffectFileManager(virtualFile)
        fun getActionName(pluginName: String) = pluginName + ACTION_SUFFIX
        fun getStateName(pluginName: String) = pluginName + STATE_SUFFIX
        fun getStateSliceName(pluginName: String) = pluginName + STATE_SLICE_SUFFIX
        fun getNavName(pluginName: String) = pluginName + NAV_SUFFIX
        fun createNewInstance(insertionPackage: PluginPackage): PluginEffectFileManager? {
            val fileName = getFileName(insertionPackage.pluginName)
            return insertionPackage.createNewFile(
                fileName,
                PluginEffectTemplate(
                    packageManager = insertionPackage,
                    fileName = fileName
                ).createContent()
            )?.let { PluginEffectFileManager(it) }
        }
    }
}