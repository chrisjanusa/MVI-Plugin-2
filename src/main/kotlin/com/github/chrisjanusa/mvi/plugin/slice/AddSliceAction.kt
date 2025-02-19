package com.github.chrisjanusa.mvi.plugin.slice


import com.github.chrisjanusa.mvi.document_management.StateSliceDocumentManager
import com.github.chrisjanusa.mvi.file_managment.capitalize
import com.github.chrisjanusa.mvi.file_managment.createSubDirectory
import com.github.chrisjanusa.mvi.file_managment.getDirectory
import com.github.chrisjanusa.mvi.file_managment.getFeaturePackageFile
import com.github.chrisjanusa.mvi.file_managment.getPluginPackageFile
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
        val pluginDir = pluginPackage.getDirectory(event) ?: return
        PluginSliceFileTemplate(pluginName).createFileInDir(event, pluginDir)
        pluginDir.createSubDirectory("generated") { generatedDir ->
            PluginSliceUpdateFileTemplate(
                featurePackage.name,
                pluginName,
            ).createFileInDir(event, generatedDir)
        }
        val pluginCapitalized = pluginName.capitalize()
        addSliceToAction(pluginCapitalized, pluginPackage, event)
        addSliceToPlugin(pluginCapitalized, pluginPackage, event)
        addSliceToViewModel(pluginCapitalized, pluginPackage, event)
        addSliceToContent(pluginCapitalized, pluginPackage, event)
        addSliceToTypeAlias(pluginCapitalized, pluginPackage, event)
    }

    private fun addSliceToAction(pluginCapitalized: String, pluginPackage: VirtualFile, event: AnActionEvent) {
        val actionFile = pluginPackage.findChild("${pluginCapitalized}Action") ?: return
        val document = FileDocumentManager.getInstance().getDocument(actionFile) ?: return
        val documentManager = StateSliceDocumentManager(document, event)
        documentManager.addSlice()
        documentManager.writeToDisk()
    }

    private fun addSliceToPlugin(pluginCapitalized: String, pluginPackage: VirtualFile, event: AnActionEvent) {
        val generatedPackage = pluginPackage.findChild("generated")
        val file = generatedPackage?.findChild("${pluginCapitalized}Plugin") ?: return
        val document = FileDocumentManager.getInstance().getDocument(file) ?: return
        val documentManager = StateSliceDocumentManager(document, event)
        documentManager.findAndReplaceIfNotExists(
            oldValue = "            modifier = modifier, \n",
            newValue = "            modifier = modifier, \n" +
                       "            slice = slice, \n",
            existStringCheck = "            slice = slice, \n"
        )
        documentManager.addSlice()
        documentManager.writeToDisk()
    }

    private fun addSliceToViewModel(pluginCapitalized: String, pluginPackage: VirtualFile, event: AnActionEvent) {
        val generatedPackage = pluginPackage.findChild("generated")
        val file = generatedPackage?.findChild("${pluginCapitalized}ViewModel") ?: return
        val document = FileDocumentManager.getInstance().getDocument(file) ?: return
        val documentManager = StateSliceDocumentManager(document, event)
        documentManager.findAndReplace(
            oldValue = "    override val initialSlice = NoSlice\n",
            newValue = "    override val initialSlice = ${pluginCapitalized}Slice()\n" +
                    "    override fun getSliceFlow() =\n" +
                    "        parentViewModel?.getFilteredSliceUpdateFlow<${pluginCapitalized}SliceUpdate>()?.map { it.slice }\n",

            )
        documentManager.addSlice()
        documentManager.writeToDisk()
    }

    private fun addSliceToTypeAlias(pluginCapitalized: String, pluginPackage: VirtualFile, event: AnActionEvent) {
        val generatedPackage = pluginPackage.findChild("generated")
        val file = generatedPackage?.findChild("${pluginCapitalized}TypeAlias") ?: return
        val document = FileDocumentManager.getInstance().getDocument(file) ?: return
        val documentManager = StateSliceDocumentManager(document, event)
        documentManager.addSlice()
        documentManager.writeToDisk()
    }

    private fun addSliceToContent(pluginCapitalized: String, pluginPackage: VirtualFile, event: AnActionEvent) {
        val generatedPackage = pluginPackage.findChild("generated")
        val file = generatedPackage?.findChild("${pluginCapitalized}Content") ?: return
        val document = FileDocumentManager.getInstance().getDocument(file) ?: return
        val documentManager = StateSliceDocumentManager(document, event)
        documentManager.findAndReplaceIfNotExists(
            oldValue = "    modifier: Modifier, \n",
            newValue = "    modifier: Modifier, \n" +
                       "    slice: ${pluginCapitalized}Slice, \n",
            existStringCheck = "    slice: ${pluginCapitalized}Slice, \n"
        )
        documentManager.addSlice()
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