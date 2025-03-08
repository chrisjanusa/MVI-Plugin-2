package com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.generated

import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.helper.IPluginFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.helper.PluginFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.helper.PluginFileNameProvider
import com.intellij.openapi.vfs.VirtualFile

class PluginNavDestinationFileManager(
    file: VirtualFile,
    pluginFileManager: PluginFileManager = PluginFileManager(file)
) : IPluginFileManager by pluginFileManager, FileManager(file) {

    companion object : PluginFileNameProvider("NavDestination") {
        override fun createInstance(virtualFile: VirtualFile) = PluginNavDestinationFileManager(virtualFile)
        fun createNewInstance(insertionPackage: PluginGeneratedPackage): PluginNavDestinationFileManager? {
            val fileName = getFileName(insertionPackage.pluginName)
            val navDestination = insertionPackage.createNewFile(
                fileName,
                PluginNavDestinationTemplate(insertionPackage, fileName)
                    .createContent()
            )?.let { PluginNavDestinationFileManager(it) }
            if (navDestination != null) {
                insertionPackage.featurePackage.navPackage?.navGraph?.addNavDestination(navDestination)
            }
            return navDestination
        }
    }
}