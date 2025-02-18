package com.github.chrisjanusa.mvi.feature
import com.github.chrisjanusa.mvi.feature.nav.NavGraphFileTemplate
import com.github.chrisjanusa.mvi.feature.shared.SharedActionFileTemplate
import com.github.chrisjanusa.mvi.feature.shared.SharedEffectFileTemplate
import com.github.chrisjanusa.mvi.feature.shared.SharedStateFileTemplate
import com.github.chrisjanusa.mvi.feature.shared.SharedTypeAliasFileTemplate
import com.github.chrisjanusa.mvi.feature.shared.SharedViewModelFileTemplate
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
        root.createSubDirectory(createFeaturePromptResult.featureName.lowercase()) { featureDir ->
            featureDir.createSubDirectory("plugin")
            if (createFeaturePromptResult.createSharedState || createFeaturePromptResult.createNavGraph) {
                featureDir.createSubDirectory("nav") { navDir ->
                    NavGraphFileTemplate(createFeaturePromptResult.featureName).createFileInDir(event, navDir)
                }
            }
            if (createFeaturePromptResult.createSharedState) {
                featureDir.createSubDirectory("shared") { sharedDir ->
                    SharedEffectFileTemplate(createFeaturePromptResult.featureName).createFileInDir(event, sharedDir)
                    SharedActionFileTemplate(createFeaturePromptResult.featureName).createFileInDir(event, sharedDir)
                    SharedStateFileTemplate(createFeaturePromptResult.featureName).createFileInDir(event, sharedDir)
                    sharedDir.createSubDirectory("generated") { generatedDir ->
                        SharedTypeAliasFileTemplate(createFeaturePromptResult.featureName).createFileInDir(event, generatedDir)
                        SharedViewModelFileTemplate(createFeaturePromptResult.featureName).createFileInDir(event, generatedDir)
                    }
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