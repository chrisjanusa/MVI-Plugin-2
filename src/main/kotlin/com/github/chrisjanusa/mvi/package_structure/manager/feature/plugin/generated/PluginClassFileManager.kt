package com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.generated

import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.helper.IPluginFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.helper.PluginFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.helper.PluginFileNameProvider
import com.intellij.openapi.vfs.VirtualFile

open class PluginClassFileManager(
    file: VirtualFile,
    private val pluginFileManager: PluginFileManager = PluginFileManager(file)
) : IPluginFileManager by pluginFileManager, FileManager(file) {
    override fun addSlice() {
        pluginFileManager.findAndReplaceIfNotExists(
            oldValue = "            modifier = modifier,\n",
            newValue = "            modifier = modifier,\n" +
            "            slice = slice,\n",
            existStringCheck = "            slice = slice,\n"
        )
        pluginFileManager.addSlice()
    }

    override fun removeSlice() {
        pluginFileManager.removeAllOccurrences(
            textToRemove = "            slice = slice,\n",
        )
        pluginFileManager.removeSlice()
    }

    override fun addState() {
        pluginFileManager.findAndReplaceIfNotExists(
            oldValue = "            modifier = modifier,\n",
            newValue = "            modifier = modifier,\n" +
            "            state = state,\n",
            existStringCheck = "            state = state,\n"
        )
        pluginFileManager.addState()
    }

    override fun removeState() {
        pluginFileManager.removeAllOccurrences(
            textToRemove = "            state = state,\n",
        )
        pluginFileManager.removeState()
    }

    companion object : PluginFileNameProvider("Plugin") {
        override fun createInstance(virtualFile: VirtualFile) = PluginClassFileManager(virtualFile)
        fun createNewInstance(
            insertionPackage: PluginGeneratedPackage,
            hasState: Boolean,
            hasSlice: Boolean
        ): PluginClassFileManager? {
            val fileName = getFileName(insertionPackage.pluginName)
            return insertionPackage.createNewFile(
                fileName,
                PluginClassTemplate(
                    hasState = hasState,
                    hasSlice = hasSlice,
                    packageManager = insertionPackage,
                    fileName = fileName
                ).createContent()
            )?.let { PluginClassFileManager(it) }
        }
    }
}