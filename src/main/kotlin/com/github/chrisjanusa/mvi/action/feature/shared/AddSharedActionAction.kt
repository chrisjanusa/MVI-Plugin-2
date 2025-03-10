package com.github.chrisjanusa.mvi.action.feature.shared


import com.github.chrisjanusa.mvi.action.feature.plugin.action.ActionDialog
import com.github.chrisjanusa.mvi.action.feature.plugin.action.ActionPromptResult
import com.github.chrisjanusa.mvi.action.feature.plugin.action.ActionType
import com.github.chrisjanusa.mvi.helper.file_helper.toPascalCase
import com.github.chrisjanusa.mvi.package_structure.getManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.shared.SharedActionFileManager
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class AddSharedActionAction : AnAction("Add _Action") {
    override fun actionPerformed(event: AnActionEvent) {
        val actionPromptResult = ActionPromptResult()
        if (!ActionDialog(actionPromptResult).showAndGet()) return

        val actionFileManager = event.getManager() as? SharedActionFileManager ?: return

        val actionName = actionPromptResult.actionName.toPascalCase()

        when (actionPromptResult.actionType) {
            ActionType.NO_REDUCTION -> actionFileManager.addAction(
                actionName = actionName,
                isReducible = false,
                hasParameters = actionPromptResult.hasParameters
            )

            ActionType.NAV -> {
                actionFileManager.addNavAction(
                    actionName = actionName
                )
                actionFileManager.rootPackage.appPackage?.navEffect?.addNavEffect(
                    actionFile = actionFileManager,
                    actionName = actionName
                )
            }

            ActionType.REDUCIBLE -> actionFileManager.addAction(
                actionName = actionName,
                isReducible = true,
                hasParameters = actionPromptResult.hasParameters
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
            return event.getManager() is SharedActionFileManager
        }
    }
}