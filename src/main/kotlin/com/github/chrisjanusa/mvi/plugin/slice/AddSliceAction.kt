package com.github.chrisjanusa.mvi.plugin.slice


import com.github.chrisjanusa.mvi.document_management.DocumentManager
import com.github.chrisjanusa.mvi.file_managment.capitalize
import com.github.chrisjanusa.mvi.file_managment.createSubDirectory
import com.github.chrisjanusa.mvi.file_managment.getDirFromFile
import com.github.chrisjanusa.mvi.file_managment.getFeaturePackageFile
import com.github.chrisjanusa.mvi.file_managment.getPluginPackageFile
import com.github.chrisjanusa.mvi.file_managment.getRootPackage
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.vfs.VirtualFile

class AddSliceAction : AnAction("Add _Slice") {
    override fun actionPerformed(event: AnActionEvent) {
        val featurePackage = event.getFeaturePackageFile() ?: return
        val pluginPackage = event.getPluginPackageFile() ?: return
        val pluginName = pluginPackage.name
        val pluginDir = event.getDirFromFile(pluginPackage) ?: return
        PluginSliceFileTemplate(pluginName).createFileInDir(event, pluginDir)
        pluginDir.createSubDirectory("generated") { generatedDir ->
            PluginSliceUpdateFileTemplate(
                featurePackage.name,
                pluginName,
            ).createFileInDir(event, generatedDir)
        }
        val pluginCapitalized = pluginName.capitalize()
        val sliceName = "${pluginCapitalized}Slice"
        val rootPackage = event.getRootPackage()
        val slicePackage = "import $rootPackage.$featurePackage.plugin.$pluginPackage.$sliceName"
        val noSlicePackage = "import $rootPackage.foundation.state.NoSlice"
        addSliceToAction(sliceName, pluginCapitalized, pluginPackage)
        addSliceToPlugin(sliceName, pluginCapitalized, pluginPackage, noSlicePackage, slicePackage)
        addSliceToViewModel(sliceName, pluginCapitalized, pluginPackage, noSlicePackage, slicePackage)
        addSliceToContent(sliceName, pluginCapitalized, pluginPackage, noSlicePackage, slicePackage)
        addSliceToTypeAlias(sliceName, pluginCapitalized, pluginPackage, noSlicePackage, slicePackage)
    }

    private fun addSliceToAction(sliceName: String, pluginCapitalized: String, pluginPackage: VirtualFile) {
        val actionFile = pluginPackage.findChild("${pluginCapitalized}Action") ?: return
        val document = FileDocumentManager.getInstance().getDocument(actionFile) ?: return
        val documentManager = DocumentManager(document)
        documentManager.findAndReplace("NoSlice", sliceName)
        documentManager.writeToDisk()
    }

    private fun addSliceToPlugin(sliceName: String, pluginCapitalized: String, pluginPackage: VirtualFile, noSlicePackage: String, slicePackage: String) {
        val generatedPackage = pluginPackage.findChild("generated")
        val file = generatedPackage?.findChild("${pluginCapitalized}Plugin") ?: return
        val document = FileDocumentManager.getInstance().getDocument(file) ?: return
        val documentManager = DocumentManager(document)
        documentManager.findAndReplace(noSlicePackage, slicePackage)
        documentManager.findAndReplace("NoSlice", sliceName)
        documentManager.findAndReplaceIfNotExists(
            oldValue = "            modifier = modifier, \n",
            newValue = "            modifier = modifier, \n" +
                       "            slice = slice, \n",
            existStringCheck = "            slice = slice, \n"
        )
        documentManager.writeToDisk()
    }

    private fun addSliceToViewModel(sliceName: String, pluginCapitalized: String, pluginPackage: VirtualFile, noSlicePackage: String, slicePackage: String) {
        val generatedPackage = pluginPackage.findChild("generated")
        val file = generatedPackage?.findChild("${pluginCapitalized}ViewModel") ?: return
        val document = FileDocumentManager.getInstance().getDocument(file) ?: return
        val documentManager = DocumentManager(document)
        documentManager.findAndReplace(noSlicePackage, slicePackage)
        documentManager.findAndReplace("NoSlice", sliceName)
        documentManager.findAndReplaceIfNotExists(
            oldValue = "    override val initialState = ${pluginCapitalized}State()\n",
            newValue = "    override val initialState = ${pluginCapitalized}State()\n" +
                       "    override val initialSlice = ${sliceName}()\n" +
                       "    override fun getSliceFlow() =\n" +
                       "        parentViewModel?.getFilteredSliceUpdateFlow<${sliceName}Update>()?.map { it.slice }\n",
            existStringCheck = "    override val initialSlice = ${sliceName}()\n" +
                               "    override fun getSliceFlow() =\n" +
                               "        parentViewModel?.getFilteredSliceUpdateFlow<${sliceName}Update>()?.map { it.slice }\n"

        )
        documentManager.writeToDisk()
    }

    private fun addSliceToTypeAlias(sliceName: String, pluginCapitalized: String, pluginPackage: VirtualFile, noSlicePackage: String, slicePackage: String) {
        val generatedPackage = pluginPackage.findChild("generated")
        val file = generatedPackage?.findChild("${pluginCapitalized}TypeAlias") ?: return
        val document = FileDocumentManager.getInstance().getDocument(file) ?: return
        val documentManager = DocumentManager(document)
        documentManager.findAndReplace(noSlicePackage, slicePackage)
        documentManager.findAndReplace("NoSlice", sliceName)
        documentManager.writeToDisk()
    }

    private fun addSliceToContent(sliceName: String, pluginCapitalized: String, pluginPackage: VirtualFile, noSlicePackage: String, slicePackage: String) {
        val generatedPackage = pluginPackage.findChild("generated")
        val file = generatedPackage?.findChild("${pluginCapitalized}Content") ?: return
        val document = FileDocumentManager.getInstance().getDocument(file) ?: return
        val documentManager = DocumentManager(document)
        documentManager.findAndReplace(noSlicePackage, slicePackage)
        documentManager.findAndReplace("NoSlice", sliceName)
        documentManager.findAndReplaceIfNotExists(
            oldValue = "    modifier: Modifier, \n",
            newValue = "    modifier: Modifier, \n" +
                       "    slice: $sliceName, \n",
            existStringCheck = "    slice: $sliceName, \n"
        )
        documentManager.writeToDisk()
    }

    override fun update(event: AnActionEvent) {
        event.presentation.isEnabledAndVisible = isEnabled(event)
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

    companion object {
        fun isEnabled(event: AnActionEvent): Boolean {
            val pluginPackage = event.getPluginPackageFile() ?: return false
            val slice = pluginPackage.findChild("${pluginPackage.name.capitalize()}Slice")
            return slice == null
        }
    }
}