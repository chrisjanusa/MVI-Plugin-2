package com.github.chrisjanusa.mvi.plugin


import com.github.chrisjanusa.mvi.file_managment.createSubDirectory
import com.github.chrisjanusa.mvi.file_managment.getDirectory
import com.github.chrisjanusa.mvi.file_managment.getFeaturePackageFile
import com.github.chrisjanusa.mvi.file_managment.isFeaturePackageOrDirectChild
import com.github.chrisjanusa.mvi.file_managment.toSnakeCase
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
        val pluginName = pluginPromptResult.pluginName.toSnakeCase()
        val hasState = pluginPromptResult.createState
        val hasSlice = pluginPromptResult.createSlice
        val isNavDestination = pluginPromptResult.createNavDestination
        pluginDirFile.getDirectory(event)?.createSubDirectory( pluginPromptResult.pluginName) { pluginDir ->
            PluginEffectFileTemplate(event, pluginName).createFileInDir(pluginDir)
            PluginActionFileTemplate(hasState, hasSlice, event, pluginName).createFileInDir(pluginDir)
            if (hasState) {
                PluginStateFileTemplate(event, pluginName).createFileInDir(pluginDir)
            }
            if (hasSlice) {
                PluginSliceFileTemplate(event, pluginName).createFileInDir(pluginDir)
            }
            pluginDir.createSubDirectory("ui") { uiDir ->
                PluginContentFileTemplate(hasState, hasSlice, event, pluginName).createFileInDir(uiDir)
            }
            pluginDir.createSubDirectory("generated") { generatedDir ->
                if (isNavDestination) {
                    PluginNavDestinationFileTemplate(event, pluginName).createFileInDir(generatedDir)
                }
                PluginTypeAliasFileTemplate(hasState, hasSlice, event, pluginName).createFileInDir(generatedDir)
                PluginViewModelFileTemplate(
                    hasState,
                    hasSlice,
                    event,
                    pluginName,
                ).createFileInDir(generatedDir)
                PluginFileTemplate(
                    hasState,
                    hasSlice,
                    event,
                    pluginName,
                ).createFileInDir(generatedDir)
                if (hasSlice) {
                    PluginSliceUpdateFileTemplate(
                        event,
                        pluginName,
                    ).createFileInDir(generatedDir)
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