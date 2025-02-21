package com.github.chrisjanusa.mvi.package_generator.feature.shared

import com.github.chrisjanusa.mvi.helper.file_managment.createSubDirectory
import com.github.chrisjanusa.mvi.helper.file_managment.getDirectory
import com.github.chrisjanusa.mvi.helper.file_managment.getFeaturePackageFile
import com.github.chrisjanusa.mvi.helper.file_managment.isFeaturePackageOrDirectChild
import com.github.chrisjanusa.mvi.helper.file_managment.toPascalCase
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class CreateSharedStateAction : AnAction("Create _Shared State") {
    override fun actionPerformed(event: AnActionEvent) {
        val featureFile = event.getFeaturePackageFile() ?: return
        featureFile.getDirectory(event)?.createSubDirectory("shared") { sharedDir ->
            SharedEffectFileTemplate(event, featureFile.name).createFileInDir(sharedDir)
            SharedActionFileTemplate(event, featureFile.name).createFileInDir(sharedDir)
            SharedStateFileTemplate(event, featureFile.name).createFileInDir(sharedDir)
            sharedDir.createSubDirectory("generated") { generatedDir ->
                SharedTypeAliasFileTemplate(event, featureFile.name).createFileInDir(generatedDir)
                SharedViewModelFileTemplate(event, featureFile.name).createFileInDir(generatedDir)
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
            val navGraphName = "${featureFile.name.toPascalCase()}SharedState.kt"
            return featureFile.findChild("shared")?.findChild(navGraphName) == null
        }
    }
}