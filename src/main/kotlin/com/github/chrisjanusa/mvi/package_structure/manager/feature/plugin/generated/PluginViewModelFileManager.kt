package com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.generated

import com.github.chrisjanusa.mvi.package_structure.manager.base.ViewModelFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.helper.IPluginFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.helper.PluginFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.helper.PluginFileNameProvider
import com.intellij.openapi.vfs.VirtualFile

class PluginViewModelFileManager(
    file: VirtualFile,
    private val pluginFileManager: PluginFileManager = PluginFileManager(file)
) : ViewModelFileManager(file), IPluginFileManager by pluginFileManager {
    override fun addSlice() {
        pluginFileManager.findAndReplace(
            oldValue = "    override val initialSlice = NoSlice\n",
            newValue = "    override val initialSlice = ${pluginName}Slice()\n"
        )

        pluginFileManager.findAndReplace(
            oldValue = "    override fun getSliceFlow(): Flow<NoSlice>? = null\n",
            newValue = "    override fun getSliceFlow() =\n" +
            "        parentViewModel?.getFilteredSliceUpdateFlow<${pluginName}SliceUpdate>()?.map { it.slice }\n",
        )
        pluginFileManager.removeImport("kotlinx.coroutines.flow.Flow")
        pluginFileManager.addImport("kotlinx.coroutines.flow.map")
        pluginFileManager.addImport("${rootPackage.foundationPackage?.parentViewModel?.packagePathExcludingFile}.getFilteredSliceUpdateFlow")
        pluginFileManager.addSlice()
    }

    override fun removeSlice() {
        pluginFileManager.findAndReplace(
            oldValue = "    override val initialSlice = ${pluginName}Slice()\n",
            newValue = "    override val initialSlice = NoSlice\n",
        )
        pluginFileManager.findAndReplace(
            oldValue = "    override fun getSliceFlow() =\n" +
            "        parentViewModel?.getFilteredSliceUpdateFlow<${pluginName}SliceUpdate>()?.map { it.slice }\n",
            newValue = "    override fun getSliceFlow(): Flow<NoSlice>? = null\n",
        )
        pluginFileManager.addImport("kotlinx.coroutines.flow.Flow")
        pluginFileManager.removeImport("kotlinx.coroutines.flow.map")
        pluginFileManager.removeImport("${rootPackage.foundationPackage?.parentViewModel?.packagePathExcludingFile}.getFilteredSliceUpdateFlow")
        pluginFileManager.removeSlice()
    }

    override fun addState() {
        pluginFileManager.findAndReplace(
            oldValue = "    override val initialState = NoState\n",
            newValue = "    override val initialState = ${pluginName}State()\n"
        )
        pluginFileManager.addState()
    }

    override fun removeState() {
        pluginFileManager.findAndReplace(
            oldValue = "    override val initialState = ${pluginName}State()\n",
            newValue = "    override val initialState = NoState\n",
        )
        pluginFileManager.removeState()
    }

    companion object : PluginFileNameProvider(SUFFIX) {
        override fun createInstance(virtualFile: VirtualFile) = PluginViewModelFileManager(virtualFile)
        fun createNewInstance(
            insertionPackage: PluginGeneratedPackage,
            hasState: Boolean,
            hasSlice: Boolean
        ): PluginViewModelFileManager? {
            val fileName = getFileName(insertionPackage.pluginName)
            val pluginViewModel = insertionPackage.createNewFile(
                fileName,
                PluginViewModelTemplate(
                    hasState = hasState,
                    hasSlice = hasSlice,
                    packageManager = insertionPackage,
                    fileName = fileName
                ).createContent()
            )?.let { PluginViewModelFileManager(it) }
            if (pluginViewModel != null) {
                insertionPackage.rootPackage.koinModule?.addPluginViewModel(pluginViewModel)
            }
            return pluginViewModel
        }
    }
}