package com.github.chrisjanusa.mvi.action.feature.plugin.slice


import com.github.chrisjanusa.mvi.helper.file_helper.toPascalCase
import com.github.chrisjanusa.mvi.package_structure.getPluginPackage
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class RemoveSliceAction : AnAction("Remove S_lice") {
    override fun actionPerformed(event: AnActionEvent) {
        val pluginPackage = event.getPluginPackage() ?: return
        val pluginName = pluginPackage.pluginName
        val pluginCapitalized = pluginName.toPascalCase()
        val removeSliceDialog = RemoveSliceDialog(pluginCapitalized)
        val isCancelled = !removeSliceDialog.showAndGet()
        if (isCancelled) return
        pluginPackage.removeSlice()
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
            return pluginPackage.hasSlice
        }
    }
}