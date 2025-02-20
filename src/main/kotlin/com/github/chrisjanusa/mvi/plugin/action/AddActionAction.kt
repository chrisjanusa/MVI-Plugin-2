package com.github.chrisjanusa.mvi.plugin.action


import com.github.chrisjanusa.mvi.document_management.ActionDocumentManager
import com.github.chrisjanusa.mvi.file_managment.getCurrentFile
import com.github.chrisjanusa.mvi.file_managment.getPluginPackageFile
import com.github.chrisjanusa.mvi.file_managment.toPascalCase
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileDocumentManager

class AddActionAction : AnAction("Add _Action") {
    override fun actionPerformed(event: AnActionEvent) {
        val actionPromptResult = ActionPromptResult()
        if (!ActionDialog(actionPromptResult).showAndGet()) return

        val actionFile = event.getCurrentFile() ?: return
        val actionDocument = FileDocumentManager.getInstance().getDocument(actionFile) ?: return
        val actionDocumentManager = ActionDocumentManager(actionDocument)

        val actionName = actionPromptResult.actionName.toPascalCase()

        when (actionPromptResult.actionType) {
            ActionType.NO_REDUCTION -> actionDocumentManager.addAction(
                actionName = actionName,
                isReducible = false,
                hasParameters = actionPromptResult.hasParameters
            )

            ActionType.NAV -> actionDocumentManager.addNavAction(
                actionName = actionName
            )
            ActionType.REDUCIBLE -> actionDocumentManager.addAction(
                actionName = actionName,
                isReducible = true,
                hasParameters = actionPromptResult.hasParameters
            )
        }

        actionDocumentManager.writeToDisk()
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
            return event.getCurrentFile()?.name == "${pluginPackage.name.toPascalCase()}Action.kt"
        }
    }
}