package com.github.chrisjanusa.mvi.feature.shared

import com.github.chrisjanusa.mvi.file_managment.capitalize
import com.github.chrisjanusa.mvi.file_managment.createSubDirectory
import com.github.chrisjanusa.mvi.file_managment.getDirFromFile
import com.github.chrisjanusa.mvi.file_managment.getFeaturePackageFile
import com.github.chrisjanusa.mvi.file_managment.isFeaturePackageOrDirectChild
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class CreateSharedStateAction : AnAction("Create _Shared State") {
    override fun actionPerformed(event: AnActionEvent) {
        val featureFile = event.getFeaturePackageFile() ?: return
        event.getDirFromFile(featureFile)?.createSubDirectory("shared") { sharedDir ->
            SharedEffectFileTemplate(featureFile.name).createFileInDir(event, sharedDir)
            SharedActionFileTemplate(featureFile.name).createFileInDir(event, sharedDir)
            SharedStateFileTemplate(featureFile.name).createFileInDir(event, sharedDir)
            sharedDir.createSubDirectory("generated") { generatedDir ->
                SharedTypeAliasFileTemplate(featureFile.name).createFileInDir(event, generatedDir)
                SharedViewModelFileTemplate(featureFile.name).createFileInDir(event, generatedDir)
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
            val featureFile = event.getFeaturePackageFile() ?: return false
            val navGraphName = "${featureFile.name.capitalize()}SharedState.kt"
            return featureFile.findChild("shared")?.findChild(navGraphName) == null
        }
    }
}