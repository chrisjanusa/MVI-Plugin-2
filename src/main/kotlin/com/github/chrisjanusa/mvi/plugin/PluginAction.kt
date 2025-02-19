package com.github.chrisjanusa.mvi.plugin


import com.github.chrisjanusa.mvi.file_managment.createSubDirectory
import com.github.chrisjanusa.mvi.file_managment.getDirFromFile
import com.github.chrisjanusa.mvi.file_managment.getFeaturePackageFile
import com.github.chrisjanusa.mvi.file_managment.isFeaturePackageOrDirectChild
import com.github.chrisjanusa.mvi.plugin.file_templates.PluginActionFileTemplate
import com.github.chrisjanusa.mvi.plugin.file_templates.PluginContentFileTemplate
import com.github.chrisjanusa.mvi.plugin.file_templates.PluginEffectFileTemplate
import com.github.chrisjanusa.mvi.plugin.file_templates.PluginFileTemplate
import com.github.chrisjanusa.mvi.plugin.file_templates.PluginNavDestinationFileTemplate
import com.github.chrisjanusa.mvi.plugin.file_templates.PluginStateFileTemplate
import com.github.chrisjanusa.mvi.plugin.file_templates.PluginTypeAliasFileTemplate
import com.github.chrisjanusa.mvi.plugin.file_templates.PluginViewModelFileTemplate
import com.github.chrisjanusa.mvi.plugin.slice.PluginSliceFileTemplate
import com.github.chrisjanusa.mvi.plugin.slice.PluginSliceUpdateFileTemplate
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class PluginAction : AnAction("Add _Plugin") {
    override fun actionPerformed(event: AnActionEvent) {
        val featurePackage = event.getFeaturePackageFile()
        val pluginPromptResult = PluginPromptResult()
        val dialog = PluginDialog(pluginPromptResult)
        val isCancelled = !dialog.showAndGet()
        if (isCancelled) return
        val pluginDirFile = featurePackage?.findChild("plugin") ?: return
        val pluginName = pluginPromptResult.pluginName
        val hasState = pluginPromptResult.createState
        val hasSlice = pluginPromptResult.createSlice
        val isNavDestination = pluginPromptResult.createNavDestination
        event.getDirFromFile(pluginDirFile)?.createSubDirectory( pluginPromptResult.pluginName) { pluginDir ->
            PluginEffectFileTemplate(pluginName).createFileInDir(event, pluginDir)
            PluginActionFileTemplate(pluginName, hasState, hasSlice).createFileInDir(event, pluginDir)
            if (hasState) {
                PluginStateFileTemplate(pluginName).createFileInDir(event, pluginDir)
            }
            if (hasSlice) {
                PluginSliceFileTemplate(pluginName).createFileInDir(event, pluginDir)
            }
            pluginDir.createSubDirectory("ui") { uiDir ->
                PluginContentFileTemplate(featurePackage.name, pluginName, hasState, hasSlice).createFileInDir(event, uiDir)
            }
            pluginDir.createSubDirectory("generated") { generatedDir ->
                if (isNavDestination) {
                    PluginNavDestinationFileTemplate(pluginName).createFileInDir(event, generatedDir)
                }
                PluginTypeAliasFileTemplate(featurePackage.name, pluginName, hasState, hasSlice).createFileInDir(event, generatedDir)
                PluginViewModelFileTemplate(
                    featurePackage.name,
                    pluginName,
                    hasState,
                    hasSlice
                ).createFileInDir(event, generatedDir)
                PluginFileTemplate(
                    featurePackage.name,
                    pluginName,
                    hasState,
                    hasSlice
                ).createFileInDir(event, generatedDir)
                if (hasSlice) {
                    PluginSliceUpdateFileTemplate(
                        featurePackage.name,
                        pluginName,
                    ).createFileInDir(event, generatedDir)
                }
            }
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
            if (!event.isFeaturePackageOrDirectChild()) return false
            event.getFeaturePackageFile() ?: return false
            return true
        }
    }
}