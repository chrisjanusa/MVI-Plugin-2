package com.github.chrisjanusa.mvi.feature.domain_model
import com.github.chrisjanusa.mvi.file_managment.capitalize
import com.github.chrisjanusa.mvi.file_managment.createSubDirectory
import com.github.chrisjanusa.mvi.file_managment.getDirectory
import com.github.chrisjanusa.mvi.file_managment.getFeaturePackageFile
import com.github.chrisjanusa.mvi.file_managment.isInsideFeaturePackage
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class CreateDomainModelAction : AnAction("Create _Domain Model") {
    override fun actionPerformed(event: AnActionEvent) {
        val featureDirFile = event.getFeaturePackageFile() ?: return
        val featureDir = featureDirFile.getDirectory(event) ?: return
        val featureName = featureDirFile.name.capitalize()
        val createDomainModelPromptResult = CreateDomainModelPromptResult(domainModelName = featureName)
        val dialog = CreateDomainModelDialog(createDomainModelPromptResult)
        val isCancelled = !dialog.showAndGet()
        if (isCancelled) return
        featureDir.createSubDirectory("domain_model") { domainModelDir ->
            DomainModelFileTemplate(createDomainModelPromptResult.domainModelName).createFileInDir(event, domainModelDir)
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