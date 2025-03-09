package com.github.chrisjanusa.mvi.action.feature.shared


import com.github.chrisjanusa.mvi.action.feature.plugin.effect.EffectDialog
import com.github.chrisjanusa.mvi.action.feature.plugin.effect.EffectPromptResult
import com.github.chrisjanusa.mvi.action.feature.plugin.effect.EffectType
import com.github.chrisjanusa.mvi.action.feature.plugin.effect.noActionFilterText
import com.github.chrisjanusa.mvi.action.feature.plugin.effect.noNavActionText
import com.github.chrisjanusa.mvi.helper.file_helper.toPascalCase
import com.github.chrisjanusa.mvi.package_structure.getManager
import com.github.chrisjanusa.mvi.package_structure.getRootPackage
import com.github.chrisjanusa.mvi.package_structure.manager.feature.shared.SharedEffectFileManager
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class AddSharedEffectAction : AnAction("Add _Effect") {
    override fun actionPerformed(event: AnActionEvent) {
        val effectManager = event.getManager() as? SharedEffectFileManager ?: return

        val sharedAction = effectManager.sharedPackage.action
        val regularActionNames = sharedAction?.getAllRegularActions() ?: emptyList()
        val sharedNavActionNames = sharedAction?.getAllNavActions() ?: listOf()

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
                sharedNavActionNames + coreNavActionNames,
                false
            ).showAndGet()
        ) return

        val actionToFilterFor = effectPromptResult.actionToFilterFor.takeIf { it != noActionFilterText }

        val effectName = effectPromptResult.effectName.toPascalCase()

        when (effectPromptResult.effectType) {
            EffectType.ACTION_ONLY -> effectManager.addActionOnlyEffect(
                effectName = effectName,
                actionToFilterFor = actionToFilterFor,
            )

            EffectType.STATE_ACTION -> effectManager.addStateEffect(
                effectName = effectName,
                actionToFilterFor = actionToFilterFor,
            )

            EffectType.SLICE_STATE_ACTION -> effectManager.addStateSliceEffect(
                effectName = effectName,
                actionToFilterFor = actionToFilterFor,
            )

            EffectType.NAV -> effectManager.addNavEffect(
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
            return event.getManager() is SharedEffectFileManager
        }
    }
}