package com.github.chrisjanusa.mvi.feature.repository

import com.github.chrisjanusa.mvi.file_managment.createSubDirectory
import com.github.chrisjanusa.mvi.file_managment.getDirectory
import com.github.chrisjanusa.mvi.file_managment.getFeaturePackageFile
import com.github.chrisjanusa.mvi.file_managment.isInsideFeaturePackage
import com.github.chrisjanusa.mvi.file_managment.toPascalCase
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class RepositoryAction : AnAction("Create _Repository") {
    override fun actionPerformed(event: AnActionEvent) {
        val featureDirFile = event.getFeaturePackageFile() ?: return
        val featureDir = featureDirFile.getDirectory(event) ?: return
        val featureName = featureDirFile.name.toPascalCase()
        val repositoryPromptResult = RepositoryPromptResult(name = featureName)
        val dialog = RepositoryDialog(repositoryPromptResult)
        val isCancelled = !dialog.showAndGet()
        if (isCancelled) return
        featureDir.createSubDirectory("api") { domainModelDir ->
            IRepositoryFileTemplate(repositoryPromptResult.name.toPascalCase(), event).createFileInDir(domainModelDir)
        }
        featureDir.createSubDirectory("service") { serviceDir ->
            serviceDir.createSubDirectory("repository") { repositoryDir ->
                RepositoryFileTemplate(repositoryPromptResult.name.toPascalCase(), event).createFileInDir(repositoryDir)
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
            return event.isInsideFeaturePackage()
        }
    }
}