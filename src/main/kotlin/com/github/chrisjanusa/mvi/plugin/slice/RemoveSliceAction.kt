package com.github.chrisjanusa.mvi.plugin.slice


import com.github.chrisjanusa.mvi.document_management.DocumentManager
import com.github.chrisjanusa.mvi.document_management.StateSliceDocumentManager
import com.github.chrisjanusa.mvi.file_managment.capitalize
import com.github.chrisjanusa.mvi.file_managment.deleteFileInDirectory
import com.github.chrisjanusa.mvi.file_managment.getDirectory
import com.github.chrisjanusa.mvi.file_managment.getPluginPackageFile
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.vfs.VirtualFile

class RemoveSliceAction : AnAction("Add _Slice") {
    override fun actionPerformed(event: AnActionEvent) {
        val pluginPackage = event.getPluginPackageFile() ?: return
        val pluginName = pluginPackage.name
        val pluginCapitalized = pluginName.capitalize()
        val removeSliceDialog = RemoveSliceDialog(pluginCapitalized)
        val isCancelled = !removeSliceDialog.showAndGet()
        if (isCancelled) return
        val pluginDir = pluginPackage.getDirectory(event) ?: return
        val sliceName = "${pluginCapitalized}Slice"
        pluginDir.deleteFileInDirectory(sliceName)
        pluginDir.findSubdirectory("generated")?.deleteFileInDirectory("${pluginCapitalized}SliceUpdate")
        removeSliceFromAction(pluginCapitalized, pluginPackage, event)
        removeSliceFromPlugin(pluginCapitalized, pluginPackage, event)
        removeSliceFromViewModel(pluginCapitalized, pluginPackage, event)
        removeSliceFromContent(pluginCapitalized, pluginPackage, event)
        removeSliceFromTypeAlias(pluginCapitalized, pluginPackage, event)
    }

    private fun removeSliceFromAction(pluginCapitalized: String, pluginPackage: VirtualFile, event: AnActionEvent) {
        val actionFile = pluginPackage.findChild("${pluginCapitalized}Action") ?: return
        val document = FileDocumentManager.getInstance().getDocument(actionFile) ?: return
        val documentManager = StateSliceDocumentManager(document, event)
        documentManager.removeSlice()
        documentManager.writeToDisk()
    }

    private fun removeSliceFromPlugin(pluginCapitalized: String, pluginPackage: VirtualFile, event: AnActionEvent) {
        val generatedPackage = pluginPackage.findChild("generated")
        val file = generatedPackage?.findChild("${pluginCapitalized}Plugin") ?: return
        val document = FileDocumentManager.getInstance().getDocument(file) ?: return
        val documentManager = StateSliceDocumentManager(document, event)
        documentManager.removeSlice()
        documentManager.removeAllOccurrences(
            textToRemove = "            slice = slice, \n",
        )
        documentManager.writeToDisk()
    }

    private fun removeSliceFromViewModel(pluginCapitalized: String, pluginPackage: VirtualFile, event: AnActionEvent) {
        val generatedPackage = pluginPackage.findChild("generated")
        val file = generatedPackage?.findChild("${pluginCapitalized}ViewModel") ?: return
        val document = FileDocumentManager.getInstance().getDocument(file) ?: return
        val documentManager = StateSliceDocumentManager(document, event)
        documentManager.findAndReplace(
            oldValue = "    override val initialSlice = ${pluginCapitalized}Slice()\n",
            newValue = "    override val initialSlice = NoSlice\n",
        )
        documentManager.removeAllOccurrences(
            textToRemove = "    override fun getSliceFlow() =\n" +
                    "        parentViewModel?.getFilteredSliceUpdateFlow<${pluginCapitalized}SliceUpdate>()?.map { it.slice }\n")
        documentManager.removeSlice()
        documentManager.writeToDisk()
    }

    private fun removeSliceFromTypeAlias(pluginCapitalized: String, pluginPackage: VirtualFile, event: AnActionEvent) {
        val generatedPackage = pluginPackage.findChild("generated")
        val file = generatedPackage?.findChild("${pluginCapitalized}TypeAlias") ?: return
        val document = FileDocumentManager.getInstance().getDocument(file) ?: return
        val documentManager = StateSliceDocumentManager(document, event)
        documentManager.removeSlice()
        documentManager.writeToDisk()
    }

    private fun removeSliceFromContent(pluginCapitalized: String, pluginPackage: VirtualFile, event: AnActionEvent) {
        val generatedPackage = pluginPackage.findChild("generated")
        val file = generatedPackage?.findChild("${pluginCapitalized}Content") ?: return
        val document = FileDocumentManager.getInstance().getDocument(file) ?: return
        val documentManager = StateSliceDocumentManager(document, event)
        documentManager.removeAllOccurrences(
            textToRemove = "    slice: ${pluginCapitalized}Slice, \n",
        )
        documentManager.removeSlice()
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
            val slice = pluginPackage.findChild("${pluginPackage.name.capitalize()}Slice")
            return slice != null
        }
    }
}