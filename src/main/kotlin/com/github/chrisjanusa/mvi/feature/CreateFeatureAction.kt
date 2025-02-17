package com.github.chrisjanusa.mvi.feature

import com.github.chrisjanusa.mvi.file_managment.createSubDirectory
import com.github.chrisjanusa.mvi.file_managment.getRootDir
import com.github.chrisjanusa.mvi.file_managment.isRootPackageOrDirectChild
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class CreateFeatureAction : AnAction("Create _Feature") {
    override fun actionPerformed(event: AnActionEvent) {
        val createFeaturePromptResult = CreateFeaturePromptResult()
        val dialog = CreateFeatureDialog(createFeaturePromptResult)
        val isCancelled = !dialog.showAndGet()
        if (isCancelled) return
        val root = event.getRootDir() ?: return
        createSubDirectory(root, createFeaturePromptResult.featureName.lowercase()) { featureDir ->
            createSubDirectory(featureDir, "plugin")
            if (createFeaturePromptResult.createNavGraph) {
                createSubDirectory(featureDir, "nav") { navDir ->
                    NavGraphFileTemplate(createFeaturePromptResult.featureName).createFileInDir(event, navDir)
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
            return event.isRootPackageOrDirectChild()
        }
    }
}