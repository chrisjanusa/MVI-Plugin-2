package com.github.chrisjanusa.mvi.action.feature.plugin.state


import com.github.chrisjanusa.mvi.package_structure.getPluginPackage
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class AddStateAction : AnAction("Add _State") {
    override fun actionPerformed(event: AnActionEvent) {
        val pluginPackage = event.getPluginPackage() ?: return
        pluginPackage.addState()
    }

    override fun update(event: AnActionEvent) {
        event.presentation.isEnabledAndVisible = isEnabled(event)
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

    companion object {
        fun isEnabled(event: AnActionEvent): Boolean {
            val pluginPackage = event.getPluginPackage() ?: return false
            return !pluginPackage.hasState
        }
    }
}