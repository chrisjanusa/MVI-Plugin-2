package com.github.chrisjanusa.mvi.action.feature.plugin


import com.github.chrisjanusa.mvi.helper.file_helper.isFeaturePackageOrDirectChild
import com.github.chrisjanusa.mvi.helper.file_helper.toSnakeCase
import com.github.chrisjanusa.mvi.package_structure.getFeaturePackage
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class PluginAction : AnAction("Add _Plugin") {
    override fun actionPerformed(event: AnActionEvent) {
        val pluginPromptResult = PluginPromptResult()
        val dialog = PluginDialog(pluginPromptResult)
        val isCancelled = !dialog.showAndGet()
        if (isCancelled) return

        val pluginName = pluginPromptResult.pluginName.toSnakeCase()
        val hasState = pluginPromptResult.createState
        val hasSlice = pluginPromptResult.createSlice
        val isNavDestination = pluginPromptResult.createNavDestination

        val featurePackage = event.getFeaturePackage()
        val pluginWrapperPackage = featurePackage?.pluginWrapperPackage
        pluginWrapperPackage?.createPlugin(pluginName, hasState, hasSlice, isNavDestination)
    }

    override fun update(event: AnActionEvent) {
        event.presentation.isEnabledAndVisible = isEnabled(event)
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

    companion object {
        fun isEnabled(event: AnActionEvent): Boolean {
            return event.isFeaturePackageOrDirectChild()
        }
    }
}