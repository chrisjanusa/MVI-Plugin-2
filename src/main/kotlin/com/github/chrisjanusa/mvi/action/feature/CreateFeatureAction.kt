package com.github.chrisjanusa.mvi.action.feature
import com.github.chrisjanusa.mvi.helper.file_helper.isRootPackageOrDirectChild
import com.github.chrisjanusa.mvi.helper.file_helper.toSnakeCase
import com.github.chrisjanusa.mvi.package_structure.getRootPackage
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class CreateFeatureAction : AnAction("Create _Feature") {
    override fun actionPerformed(event: AnActionEvent) {
        val createFeaturePromptResult = CreateFeaturePromptResult()
        val dialog = CreateFeatureDialog(createFeaturePromptResult)
        val isCancelled = !dialog.showAndGet()
        if (isCancelled) return
        val root = event.getRootPackage() ?: return
        val featureName = createFeaturePromptResult.featureName.toSnakeCase()
        val featurePackage = root.createFeature(featureName)
        if (createFeaturePromptResult.createSharedState || createFeaturePromptResult.createNavGraph) {
            featurePackage?.createNavGraph()
        }
        if (createFeaturePromptResult.createSharedState) {
            featurePackage?.createSharedState()
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
            return event.isRootPackageOrDirectChild() && event.getRootPackage()?.isInitialized ?: false
        }
    }
}