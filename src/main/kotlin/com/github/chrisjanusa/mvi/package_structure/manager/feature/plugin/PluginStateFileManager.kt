package com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin

import com.github.chrisjanusa.mvi.package_structure.manager.base.StateFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.helper.IPluginFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.helper.PluginFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.helper.PluginFileNameProvider
import com.intellij.openapi.vfs.VirtualFile

class PluginStateFileManager(
    file: VirtualFile,
    pluginFileManager: IPluginFileManager = PluginFileManager(file)
) : StateFileManager(file), IPluginFileManager by pluginFileManager {

    companion object : PluginFileNameProvider(SUFFIX) {
        override fun createInstance(virtualFile: VirtualFile) = PluginStateFileManager(virtualFile)
        fun createNewInstance(insertionPackage: PluginPackage): PluginStateFileManager? {
            val fileName = getFileName(insertionPackage.pluginName)
            return insertionPackage.createNewFile(
                fileName,
                PluginStateTemplate(insertionPackage, fileName)
                    .createContent()
            )?.let { PluginStateFileManager(it) }
        }
    }
}