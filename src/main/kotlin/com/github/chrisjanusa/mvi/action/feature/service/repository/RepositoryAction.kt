package com.github.chrisjanusa.mvi.action.feature.service.repository

import com.github.chrisjanusa.mvi.helper.file_helper.isInsideFeaturePackage
import com.github.chrisjanusa.mvi.helper.file_helper.toPascalCase
import com.github.chrisjanusa.mvi.package_structure.getFeaturePackage
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class RepositoryAction : AnAction("Create _Repository") {
    override fun actionPerformed(event: AnActionEvent) {
        val featurePackage = event.getFeaturePackage() ?: return
        val featureName = featurePackage.featureName

        val repositoryPromptResult = RepositoryPromptResult(name = featureName)
        val dialog = RepositoryDialog(repositoryPromptResult)
        val isCancelled = !dialog.showAndGet()
        if (isCancelled) return

        val apiFileManager = featurePackage.createApiPackage()?.createRepository(repositoryPromptResult.name.toPascalCase())
        val repositoryFileManager = featurePackage.createServicePackage()?.createRepositoryPackage()?.createRepository(repositoryPromptResult.name.toPascalCase())
        if (repositoryFileManager != null && apiFileManager != null) {
            val koinModule = featurePackage.rootPackage.koinModule
            koinModule?.addRepository(repositoryFileManager, apiFileManager)
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