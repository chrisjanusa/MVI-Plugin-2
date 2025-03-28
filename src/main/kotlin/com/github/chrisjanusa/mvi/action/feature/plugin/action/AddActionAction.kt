package com.github.chrisjanusa.mvi.action.feature.plugin.action


import com.github.chrisjanusa.mvi.helper.file_helper.toPascalCase
import com.github.chrisjanusa.mvi.package_structure.getManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.PluginActionFileManager
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class AddActionAction : AnAction("Add _Action") {
    override fun actionPerformed(event: AnActionEvent) {
        val actionPromptResult = ActionPromptResult()
        if (!ActionDialog(actionPromptResult).showAndGet()) return

        val pluginActionFileManager = event.getManager() as? PluginActionFileManager ?: return

        val actionName = actionPromptResult.actionName.toPascalCase()

        when (actionPromptResult.actionType) {
            ActionType.NO_REDUCTION -> pluginActionFileManager.addAction(
                actionName = actionName,
                isReducible = false,
                hasParameters = actionPromptResult.hasParameters
            )

            ActionType.NAV -> {
                pluginActionFileManager.addNavAction(
                    actionName = actionName
                )
                pluginActionFileManager.rootPackage.appPackage?.navEffect?.addNavEffect(
                    actionFile = pluginActionFileManager,
                    actionName = actionName
                )
            }
            ActionType.REDUCIBLE -> pluginActionFileManager.addAction(
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
            return event.getManager() is PluginActionFileManager
        }
    }
}