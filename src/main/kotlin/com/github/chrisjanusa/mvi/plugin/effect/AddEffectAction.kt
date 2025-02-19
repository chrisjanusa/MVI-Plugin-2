package com.github.chrisjanusa.mvi.plugin.effect


import com.github.chrisjanusa.mvi.document_management.ActionDocumentManager
import com.github.chrisjanusa.mvi.document_management.EffectDocumentManager
import com.github.chrisjanusa.mvi.document_management.ViewModelDocumentManager
import com.github.chrisjanusa.mvi.file_managment.capitalize
import com.github.chrisjanusa.mvi.file_managment.getCurrentFile
import com.github.chrisjanusa.mvi.file_managment.getDocument
import com.github.chrisjanusa.mvi.file_managment.getPluginPackageFile
import com.github.chrisjanusa.mvi.file_managment.getRootPackageFile
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileDocumentManager

class AddEffectAction : AnAction("Add _Effect") {
    override fun actionPerformed(event: AnActionEvent) {
        val pluginPackage = event.getPluginPackageFile() ?: return
        val pluginName = pluginPackage.name
        val pluginCapitalized = pluginName.capitalize()

        val actionDocument = pluginPackage.findChild("${pluginCapitalized}Action").getDocument()
        val pluginActionDocumentManager = actionDocument?.let { ActionDocumentManager(it) }
        val regularActionNames = pluginActionDocumentManager?.getAllRegularActions() ?: emptyList()
        val pluginNavActionNames = pluginActionDocumentManager?.getAllNavActions() ?: listOf()

        val coreNavActionDocument = event.getRootPackageFile()?.findChild("common")?.findChild("CoreNavAction").getDocument()
        val coreNavActionDocumentManager = coreNavActionDocument?.let { ActionDocumentManager(it) }
        val coreNavActionNames = coreNavActionDocumentManager?.getAllNavActions() ?: listOf()

        val effectPromptResult = EffectPromptResult()
        if (!EffectDialog(effectPromptResult, regularActionNames, pluginNavActionNames + coreNavActionNames).showAndGet()) return

        val generatedPackage = pluginPackage.findChild("generated")
        val file = generatedPackage?.findChild("${pluginCapitalized}ViewModel") ?: return
        val viewModelDocument = FileDocumentManager.getInstance().getDocument(file) ?: return

        val effectFile = event.getCurrentFile() ?: return
        val effectDocument = FileDocumentManager.getInstance().getDocument(effectFile) ?: return
        val effectDocumentManager = EffectDocumentManager(effectDocument, event)

        when (effectPromptResult.effectType) {
            EffectType.ACTION_ONLY -> effectDocumentManager.addActionOnlyEffect(
                effectName = effectPromptResult.effectName,
                actionToFilterFor = effectPromptResult.actionToFilterFor,
            )

            EffectType.STATE_ACTION -> effectDocumentManager.addStateEffect(
                effectName = effectPromptResult.effectName,
                actionToFilterFor = effectPromptResult.actionToFilterFor,
            )

            EffectType.SLICE_STATE_ACTION -> effectDocumentManager.addStateSliceEffect(
                effectName = effectPromptResult.effectName,
                actionToFilterFor = effectPromptResult.actionToFilterFor,
            )

            EffectType.NAV -> effectDocumentManager.addNavEffect(
                effectName = effectPromptResult.effectName,
                actionToFilterFor = effectPromptResult.actionToFilterFor,
                navAction = effectPromptResult.navAction,
                isCoreAction = coreNavActionNames.contains(effectPromptResult.navAction)
            )
        }

        val viewModelDocumentManager = ViewModelDocumentManager(viewModelDocument)
        viewModelDocumentManager.addEffect(effectPromptResult.effectName)
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
            return event.getCurrentFile()?.name == "${pluginPackage.name.capitalize()}Effect"
        }
    }
}