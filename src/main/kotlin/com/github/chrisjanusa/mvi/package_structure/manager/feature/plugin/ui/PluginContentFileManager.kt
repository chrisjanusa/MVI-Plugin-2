package com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.ui

import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.helper.IPluginFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.helper.PluginFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.helper.PluginFileNameProvider
import com.intellij.openapi.vfs.VirtualFile

class PluginContentFileManager(
    file: VirtualFile,
    private val pluginFileManager: PluginFileManager = PluginFileManager(file)
) : IPluginFileManager by pluginFileManager, FileManager(file) {
    override fun addSlice() {
        pluginFileManager.findAndReplaceIfNotExists(
            oldValue = "    modifier: Modifier,\n",
            newValue = "    modifier: Modifier,\n" +
            "    slice: ${pluginName}Slice,\n",
            existStringCheck = "    slice: ${pluginName}Slice,\n"
        )
        pluginFileManager.addImport("${pluginPackage.packagePath}.${pluginName}Slice")
        pluginFileManager.addSlice()
    }

    override fun removeSlice() {
        pluginFileManager.removeAllOccurrences(
            textToRemove = "    slice: ${pluginName}Slice,\n",
        )
        pluginFileManager.removeImport("${pluginPackage.packagePath}.${pluginName}Slice")
        pluginFileManager.removeSlice()
    }
    override fun addState() {
        pluginFileManager.findAndReplaceIfNotExists(
            oldValue = "    modifier: Modifier,\n",
            newValue = "    modifier: Modifier,\n" +
            "    state: ${pluginName}State,\n",
            existStringCheck = "    state: ${pluginName}State,\n"
        )
        pluginFileManager.addImport("${pluginPackage.packagePath}.${pluginName}State")
        pluginFileManager.addState()
    }

    override fun removeState() {
        pluginFileManager.removeAllOccurrences(
            textToRemove = "    state: ${pluginName}State,\n",
        )
        pluginFileManager.removeImport("${pluginPackage.packagePath}.${pluginName}State")
        pluginFileManager.removeSlice()
    }

    companion object : PluginFileNameProvider("Content") {
        override fun createInstance(virtualFile: VirtualFile) = PluginContentFileManager(virtualFile)
        fun createNewInstance(
            insertionPackage: PluginUiPackage,
            hasState: Boolean,
            hasSlice: Boolean
        ): PluginContentFileManager? {
            val fileName = getFileName(insertionPackage.pluginName)
            return insertionPackage.createNewFile(
                fileName,
                PluginContentTemplate(
                    hasState = hasState,
                    hasSlice = hasSlice,
                    packageManager = insertionPackage,
                    fileName = fileName
                ).createContent()
            )?.let { PluginContentFileManager(it) }
        }
    }
}