package com.github.chrisjanusa.mvi.plugin.slice


import com.github.chrisjanusa.mvi.document_management.DocumentManager
import com.github.chrisjanusa.mvi.document_management.StateSliceDocumentManager
import com.github.chrisjanusa.mvi.file_managment.toPascalCase
import com.github.chrisjanusa.mvi.file_managment.deleteFileInDirectory
import com.github.chrisjanusa.mvi.file_managment.findChildFile
import com.github.chrisjanusa.mvi.file_managment.getDirectory
import com.github.chrisjanusa.mvi.file_managment.getPluginPackageFile
import com.github.chrisjanusa.mvi.file_managment.getRootPackage
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.vfs.VirtualFile

class RemoveSliceAction : AnAction("Remove _Slice") {
    override fun actionPerformed(event: AnActionEvent) {
        val pluginPackage = event.getPluginPackageFile() ?: return
        val pluginName = pluginPackage.name
        val pluginCapitalized = pluginName.toPascalCase()
        val removeSliceDialog = RemoveSliceDialog(pluginCapitalized)
        val isCancelled = !removeSliceDialog.showAndGet()
        if (isCancelled) return
        val pluginDir = pluginPackage.getDirectory(event) ?: return
        pluginDir.deleteFileInDirectory("${pluginCapitalized}Slice.kt")
        pluginDir.findSubdirectory("generated")?.deleteFileInDirectory("${pluginCapitalized}SliceUpdate.kt")
        removeSliceFromAction(pluginCapitalized, pluginPackage, event)
        removeSliceFromPlugin(pluginCapitalized, pluginPackage, event)
        removeSliceFromViewModel(pluginCapitalized, pluginPackage, event)
        removeSliceFromContent(pluginCapitalized, pluginPackage, event)
        removeSliceFromTypeAlias(pluginCapitalized, pluginPackage, event)
    }

    private fun removeSliceFromAction(pluginCapitalized: String, pluginPackage: VirtualFile, event: AnActionEvent) {
        val actionFile = pluginPackage.findChildFile("${pluginCapitalized}Action") ?: return
        val document = FileDocumentManager.getInstance().getDocument(actionFile) ?: return
        val documentManager = StateSliceDocumentManager(document, event)
        documentManager.addImport("${event.getRootPackage()}.foundation.state.NoSlice")
        documentManager.removeSlice()
        documentManager.writeToDisk()
    }

    private fun removeSliceFromPlugin(pluginCapitalized: String, pluginPackage: VirtualFile, event: AnActionEvent) {
        val generatedPackage = pluginPackage.findChild("generated")
        val file = generatedPackage?.findChildFile("${pluginCapitalized}Plugin") ?: return
        val document = FileDocumentManager.getInstance().getDocument(file) ?: return
        val documentManager = StateSliceDocumentManager(document, event)
        documentManager.removeSlice()
        documentManager.removeAllOccurrences(
            textToRemove = "            slice = slice,\n",
        )
        documentManager.writeToDisk()
    }

    private fun removeSliceFromViewModel(pluginCapitalized: String, pluginPackage: VirtualFile, event: AnActionEvent) {
        val generatedPackage = pluginPackage.findChild("generated")
        val file = generatedPackage?.findChildFile("${pluginCapitalized}ViewModel") ?: return
        val document = FileDocumentManager.getInstance().getDocument(file) ?: return
        val documentManager = StateSliceDocumentManager(document, event)
        documentManager.findAndReplace(
            oldValue = "    override val initialSlice = ${pluginCapitalized}Slice()\n",
            newValue = "    override val initialSlice = NoSlice\n",
        )
        documentManager.findAndReplace(
            oldValue = "    override fun getSliceFlow() =\n" +
                       "        parentViewModel?.getFilteredSliceUpdateFlow<${pluginCapitalized}SliceUpdate>()?.map { it.slice }\n",
            newValue = "    override fun getSliceFlow(): Flow<NoSlice>? = null\n",
        )
        documentManager.addImport("kotlinx.coroutines.flow.Flow")
        documentManager.removeImport("kotlinx.coroutines.flow.map")
        documentManager.removeImport("${event.getRootPackage()}.foundation.viewmodel.getFilteredSliceUpdateFlow")
        documentManager.removeSlice()
        documentManager.writeToDisk()
    }

    private fun removeSliceFromTypeAlias(pluginCapitalized: String, pluginPackage: VirtualFile, event: AnActionEvent) {
        val generatedPackage = pluginPackage.findChild("generated")
        val file = generatedPackage?.findChildFile("${pluginCapitalized}TypeAlias") ?: return
        val document = FileDocumentManager.getInstance().getDocument(file) ?: return
        val documentManager = StateSliceDocumentManager(document, event)
        documentManager.removeSlice()
        documentManager.writeToDisk()
    }

    private fun removeSliceFromContent(pluginCapitalized: String, pluginPackage: VirtualFile, event: AnActionEvent) {
        val generatedPackage = pluginPackage.findChild("ui")
        val file = generatedPackage?.findChildFile("${pluginCapitalized}Content") ?: return
        val document = FileDocumentManager.getInstance().getDocument(file) ?: return
        val documentManager = StateSliceDocumentManager(document, event)
        documentManager.removeAllOccurrences(
            textToRemove = "    slice: ${pluginCapitalized}Slice,\n",
        )
        documentManager.removeSlicePackage()
        documentManager.writeToDisk()
    }

    private fun DocumentManager.replacePackage(noSlicePackage: String, slicePackage: String) {
        findAndReplace(slicePackage, noSlicePackage)
    }

    private fun DocumentManager.replaceClass(sliceName: String) {
        findAndReplace(sliceName, "NoSlice")
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
            val slice = pluginPackage.findChild("${pluginPackage.name.toPascalCase()}Slice.kt")
            return slice != null
        }
    }
}