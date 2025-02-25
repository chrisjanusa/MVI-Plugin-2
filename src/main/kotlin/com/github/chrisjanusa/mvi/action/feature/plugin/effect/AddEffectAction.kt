package com.github.chrisjanusa.mvi.action.feature.plugin.effect


import com.github.chrisjanusa.mvi.helper.file_helper.toPascalCase
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.PluginEffectFileManager
import com.github.chrisjanusa.mvi.package_structure.getManager
import com.github.chrisjanusa.mvi.package_structure.getPluginPackage
import com.github.chrisjanusa.mvi.package_structure.getRootPackage
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class AddEffectAction : AnAction("Add _Effect") {
    override fun actionPerformed(event: AnActionEvent) {
        val pluginPackage = event.getPluginPackage() ?: return
        val effectDocumentManager = pluginPackage.pluginEffect ?: return

        val actionPackage = pluginPackage.pluginAction
        val regularActionNames = actionPackage?.getAllRegularActions() ?: emptyList()
        val pluginNavActionNames = actionPackage?.getAllNavActions() ?: listOf()

        val coreNavActionNames = event
            .getRootPackage()
            ?.commonPackage
            ?.navPackage
            ?.coreNavAction
            ?.getAllNavActions()
            ?: listOf()

        val effectPromptResult = EffectPromptResult()
        if (!EffectDialog(
                effectPromptResult,
                regularActionNames,
                pluginNavActionNames + coreNavActionNames,
                pluginPackage.hasSlice
            ).showAndGet()
        ) return

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
    }

    override fun update(event: AnActionEvent) {
        event.presentation.isEnabledAndVisible = isEnabled(event)
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

    companion object {
        fun isEnabled(event: AnActionEvent): Boolean {
            return event.getManager() is PluginEffectFileManager
        }
    }
}