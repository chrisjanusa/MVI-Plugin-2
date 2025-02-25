package com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin

import com.github.chrisjanusa.mvi.package_structure.manager.base.SliceFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.helper.IPluginFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.helper.PluginFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.helper.PluginFileNameProvider
import com.intellij.openapi.vfs.VirtualFile

class PluginSliceFileManager(
    file: VirtualFile,
    pluginFileManager: PluginFileManager = PluginFileManager(file)
) : SliceFileManager(file), IPluginFileManager by pluginFileManager {

    companion object : PluginFileNameProvider(SUFFIX) {
        override fun createInstance(virtualFile: VirtualFile) = PluginSliceFileManager(virtualFile)
        fun createNewInstance(insertionPackage: PluginPackage): PluginSliceFileManager? {
            val fileName = getFileName(insertionPackage.pluginName)
            return insertionPackage.createNewFile(
                fileName,
                PluginSliceTemplate(insertionPackage, fileName)
                    .createContent()
            )?.let { PluginSliceFileManager(it) }
        }
    }
}