package com.github.chrisjanusa.mvi.action.feature.domain_model
import com.github.chrisjanusa.mvi.helper.file_helper.isInsideFeaturePackage
import com.github.chrisjanusa.mvi.helper.file_helper.toPascalCase
import com.github.chrisjanusa.mvi.package_structure.getFeaturePackage
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class CreateDomainModelAction : AnAction("Create _Domain Model") {
    override fun actionPerformed(event: AnActionEvent) {
        val featurePackage = event.getFeaturePackage() ?: return
        val featureName = featurePackage.featureName

        val createDomainModelPromptResult = CreateDomainModelPromptResult(domainModelName = featureName)
        val dialog = CreateDomainModelDialog(createDomainModelPromptResult)
        val isCancelled = !dialog.showAndGet()
        if (isCancelled) return

        val domainModelName = createDomainModelPromptResult.domainModelName.toPascalCase()
        featurePackage.createDomainModelPackage()?.createDomainModel(domainModelName)
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