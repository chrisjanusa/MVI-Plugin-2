package com.github.chrisjanusa.mvi.package_generator.plugin.effect


import com.github.chrisjanusa.mvi.helper.document_management.ActionDocumentManager
import com.github.chrisjanusa.mvi.helper.document_management.EffectDocumentManager
import com.github.chrisjanusa.mvi.helper.document_management.ViewModelDocumentManager
import com.github.chrisjanusa.mvi.helper.file_managment.findChildFile
import com.github.chrisjanusa.mvi.helper.file_managment.getCurrentFile
import com.github.chrisjanusa.mvi.helper.file_managment.getDocument
import com.github.chrisjanusa.mvi.helper.file_managment.getPluginPackageFile
import com.github.chrisjanusa.mvi.helper.file_managment.getRootPackageFile
import com.github.chrisjanusa.mvi.helper.file_managment.toPascalCase
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileDocumentManager

class AddEffectAction : AnAction("Add _Effect") {
    override fun actionPerformed(event: AnActionEvent) {
        val pluginPackage = event.getPluginPackageFile() ?: return
        val pluginName = pluginPackage.name
        val pluginCapitalized = pluginName.toPascalCase()
        val pluginHasSlice = pluginPackage.findChildFile("${pluginName.toPascalCase()}Slice") != null

        val actionDocument = pluginPackage.findChildFile("${pluginCapitalized}Action").getDocument()
        val pluginActionDocumentManager = actionDocument?.let { ActionDocumentManager(it) }
        val regularActionNames = pluginActionDocumentManager?.getAllRegularActions() ?: emptyList()
        val pluginNavActionNames = pluginActionDocumentManager?.getAllNavActions() ?: listOf()

        val coreNavActionDocument =
            event
                .getRootPackageFile()
                ?.findChild("common")
                ?.findChild("nav")
                ?.findChildFile("CoreNavAction")
                .getDocument()
        val coreNavActionDocumentManager = coreNavActionDocument?.let { ActionDocumentManager(it) }
        val coreNavActionNames = coreNavActionDocumentManager?.getAllNavActions() ?: listOf()

        val effectPromptResult = EffectPromptResult()
        if (!EffectDialog(
                effectPromptResult,
                regularActionNames,
                pluginNavActionNames + coreNavActionNames,
                pluginHasSlice
            ).showAndGet()
        ) return

        val generatedPackage = pluginPackage.findChild("generated")
        val file = generatedPackage?.findChildFile("${pluginCapitalized}ViewModel") ?: return
        val viewModelDocument = FileDocumentManager.getInstance().getDocument(file) ?: return

        val effectFile = event.getCurrentFile() ?: return
        val effectDocument = FileDocumentManager.getInstance().getDocument(effectFile) ?: return
        val effectDocumentManager = EffectDocumentManager(effectDocument, event)

        val actionToFilterFor = effectPromptResult.actionToFilterFor.takeIf { it != noActionFilterText }

        val effectName = effectPromptResult.effectName.toPascalCase()

        when (effectPromptResult.effectType) {
            EffectType.ACTION_ONLY -> effectDocumentManager.addActionOnlyEffect(
                effectName = effectName,
                actionToFilterFor = actionToFilterFor,
            )

            EffectType.STATE_ACTION -> effectDocumentManager.addStateEffect(
                effectName = effectName,
                actionToFilterFor = actionToFilterFor,
            )

            EffectType.SLICE_STATE_ACTION -> effectDocumentManager.addStateSliceEffect(
                effectName = effectName,
                actionToFilterFor = actionToFilterFor,
            )

            EffectType.NAV -> effectDocumentManager.addNavEffect(
                effectName = effectName,
                actionToFilterFor = actionToFilterFor,
                navAction = effectPromptResult.navAction.takeIf { it != noNavActionText },
                isCoreAction = coreNavActionNames.contains(effectPromptResult.navAction)
            )
        }
        effectDocumentManager.writeToDisk()

        val viewModelDocumentManager = ViewModelDocumentManager(viewModelDocument)
        viewModelDocumentManager.addEffect(effectName)
        viewModelDocumentManager.writeToDisk()
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
            return event.getCurrentFile()?.name == "${pluginPackage.name.toPascalCase()}Effect.kt"
        }
    }
}