package com.github.chrisjanusa.mvi.package_generator.feature
import com.github.chrisjanusa.mvi.package_generator.feature.nav.NavGraphFileTemplate
import com.github.chrisjanusa.mvi.package_generator.feature.shared.SharedActionFileTemplate
import com.github.chrisjanusa.mvi.package_generator.feature.shared.SharedEffectFileTemplate
import com.github.chrisjanusa.mvi.package_generator.feature.shared.SharedStateFileTemplate
import com.github.chrisjanusa.mvi.package_generator.feature.shared.SharedTypeAliasFileTemplate
import com.github.chrisjanusa.mvi.package_generator.feature.shared.SharedViewModelFileTemplate
import com.github.chrisjanusa.mvi.helper.file_managment.createSubDirectory
import com.github.chrisjanusa.mvi.helper.file_managment.getRootDir
import com.github.chrisjanusa.mvi.helper.file_managment.isRootPackageOrDirectChild
import com.github.chrisjanusa.mvi.helper.file_managment.toSnakeCase
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
        val featureName = createFeaturePromptResult.featureName.toSnakeCase()
        root.createSubDirectory(featureName) { featureDir ->
            featureDir.createSubDirectory("plugin")
            if (createFeaturePromptResult.createSharedState || createFeaturePromptResult.createNavGraph) {
                featureDir.createSubDirectory("nav") { navDir ->
                    NavGraphFileTemplate(event, featureName).createFileInDir(navDir)
                }
            }
            if (createFeaturePromptResult.createSharedState) {
                featureDir.createSubDirectory("shared") { sharedDir ->
                    SharedEffectFileTemplate(event, featureName).createFileInDir(sharedDir)
                    SharedActionFileTemplate(event, featureName).createFileInDir(sharedDir)
                    SharedStateFileTemplate(event, featureName).createFileInDir(sharedDir)
                    sharedDir.createSubDirectory("generated") { generatedDir ->
                        SharedTypeAliasFileTemplate(event, featureName).createFileInDir(generatedDir)
                        SharedViewModelFileTemplate(event, featureName).createFileInDir(generatedDir)
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