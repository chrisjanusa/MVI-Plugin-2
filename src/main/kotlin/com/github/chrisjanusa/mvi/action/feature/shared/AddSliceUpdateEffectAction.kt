package com.github.chrisjanusa.mvi.action.feature.shared


import com.github.chrisjanusa.mvi.package_structure.getManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.shared.SharedEffectFileManager
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class AddSliceUpdateEffectAction : AnAction("Add Slice _Update") {
    override fun actionPerformed(event: AnActionEvent) {
        val effectManager = event.getManager() as? SharedEffectFileManager ?: return

        val slices = effectManager.featurePackage.plugins.mapNotNull {
            if (it.navDestination != null) {
                it.slice
            } else {
                null
            }
        }

        val effectPromptResult = SliceUpdateEffectPromptResult()
        if (!SliceUpdateEffectDialog(
                effectPromptResult,
                slices
            ).showAndGet()
        ) return

        val slice = effectPromptResult.slice ?: return
        effectManager.addSliceUpdateEffect(slice)
    }

    override fun update(event: AnActionEvent) {
        event.presentation.isEnabledAndVisible = isEnabled(event)
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

    companion object {
        fun isEnabled(event: AnActionEvent): Boolean {
            val effectManager = event.getManager() as? SharedEffectFileManager ?: return false

            val slices = effectManager.featurePackage.plugins.mapNotNull {
                if (it.navDestination != null) {
                    it.slice
                } else {
                    null
                }
            }
            return slices.isNotEmpty()
        }
    }
}