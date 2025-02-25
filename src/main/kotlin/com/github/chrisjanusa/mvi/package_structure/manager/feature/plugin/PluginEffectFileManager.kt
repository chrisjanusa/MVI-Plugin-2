package com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin

import com.github.chrisjanusa.mvi.package_structure.manager.base.EffectFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.helper.IPluginFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.helper.PluginFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.helper.PluginFileNameProvider
import com.intellij.openapi.vfs.VirtualFile

class PluginEffectFileManager(
    file: VirtualFile,
    pluginFileManager: PluginFileManager = PluginFileManager(file)
) : EffectFileManager(file), IPluginFileManager by pluginFileManager {
    override val rootPath by lazy {
        rootPackage.packagePath
    }
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
    override val state by lazy {
        PluginStateFileManager.getFileName(pluginName)
    }
    override val slice by lazy {
        PluginSliceFileManager.getFileName(pluginName)
    }

    override fun getEffectFullName(effectName: String) = "${effectName}Effect"

    override fun addEffectToViewModel(effectName: String) {
        pluginPackage.viewModel?.addEffect(effectName)
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