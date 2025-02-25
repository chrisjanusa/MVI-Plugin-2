package com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin

import com.github.chrisjanusa.mvi.package_structure.manager.base.ActionFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.helper.IPluginFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.helper.PluginFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.helper.PluginFileNameProvider
import com.intellij.openapi.vfs.VirtualFile

class PluginActionFileManager(
    file: VirtualFile,
    pluginFileManager: PluginFileManager = PluginFileManager(file)
) : ActionFileManager(file), IPluginFileManager by pluginFileManager {

    companion object : PluginFileNameProvider(SUFFIX) {
        override fun createInstance(virtualFile: VirtualFile) = PluginActionFileManager(virtualFile)
        fun createNewInstance(insertionPackage: PluginPackage, hasState: Boolean, hasSlice: Boolean): PluginActionFileManager? {
            val fileName = getFileName(insertionPackage.pluginName)
            return insertionPackage.createNewFile(
                fileName,
                PluginActionTemplate(
                    hasState = hasState,
                    hasSlice = hasSlice,
                    packageManager = insertionPackage,
                    fileName = fileName
                ).createContent()
            )?.let { PluginActionFileManager(it) }
        }
    }
}