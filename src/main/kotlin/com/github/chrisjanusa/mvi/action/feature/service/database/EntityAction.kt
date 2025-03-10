package com.github.chrisjanusa.mvi.action.feature.service.database

import com.github.chrisjanusa.mvi.package_structure.getManager
import com.github.chrisjanusa.mvi.package_structure.getManagerOfType
import com.github.chrisjanusa.mvi.package_structure.manager.feature.service.database.DatabasePackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.DatabaseChild
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class EntityAction : AnAction("Create _Entity") {
    override fun actionPerformed(event: AnActionEvent) {
        val databasePackage = event.getManagerOfType(DatabasePackage) as? DatabasePackage ?: return

        val entityPromptResult = EntityPromptResult()
        val dialog = EntityDialog(entityPromptResult)
        val isCancelled = !dialog.showAndGet()
        if (isCancelled) return

        val entity = databasePackage.createEntity(entityPromptResult.name)
        if (entity != null) {
            databasePackage.database?.addEntity(entity)
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
            val manager = event.getManager()
            return manager is DatabaseChild || manager is DatabasePackage
        }
    }
}