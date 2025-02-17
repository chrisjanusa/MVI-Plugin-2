package com.github.chrisjanusa.mvi.app


import com.github.chrisjanusa.mvi.app.file_templates.*
import com.github.chrisjanusa.mvi.app.file_templates.common.ClassNameHelperFileTemplate
import com.github.chrisjanusa.mvi.app.file_templates.common.NavActionFileTemplate
import com.github.chrisjanusa.mvi.app.file_templates.foundation.*
import com.github.chrisjanusa.mvi.app.file_templates.foundation.nav.NavComponentFileTemplate
import com.github.chrisjanusa.mvi.app.file_templates.foundation.nav.NavComponentIdFileTemplate
import com.github.chrisjanusa.mvi.app.file_templates.foundation.state.SliceFileTemplate
import com.github.chrisjanusa.mvi.app.file_templates.foundation.state.StateFileTemplate
import com.github.chrisjanusa.mvi.app.file_templates.foundation.viewmodel.*
import com.github.chrisjanusa.mvi.file_managment.*
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class CreateAppAction : AnAction("Initialize _App") {
    override fun actionPerformed(event: AnActionEvent) {
        val rootPackage = event.getUninitializedRootPackageFile()?.getPackage() ?: ""
        val initialAppName = rootPackage.substringAfterLast(".").capitalize()
        val createAppPromptResult = CreateAppPromptResult(appName = initialAppName)
        val dialog = CreateAppDialog(createAppPromptResult)
        val isCancelled = !dialog.showAndGet()
        if (isCancelled) return
        val root = event.getUninitializedRootDir() ?: return
        createSubDirectory(root, "foundation") { foundationDir ->
            ActionFileTemplate().createFileInDir(event, foundationDir)
            EffectFileTemplate().createFileInDir(event, foundationDir)
            PluginFileTemplate().createFileInDir(event, foundationDir)
            SliceUpdateFileTemplate().createFileInDir(event, foundationDir)
            createSubDirectory(foundationDir, "nav") { navDir ->
                NavComponentIdFileTemplate().createFileInDir(event, navDir)
                NavComponentFileTemplate().createFileInDir(event, navDir)
            }
            createSubDirectory(foundationDir, "state") { stateDir ->
                StateFileTemplate().createFileInDir(event, stateDir)
                SliceFileTemplate().createFileInDir(event, stateDir)
            }
            createSubDirectory(foundationDir, "viewmodel") { viewmodelDir ->
                BaseViewModelFileTemplate().createFileInDir(event, viewmodelDir)
                NoSlicePluginViewModelFileTemplate().createFileInDir(event, viewmodelDir)
                ParentViewModelFileTemplate().createFileInDir(event, viewmodelDir)
                PluginViewModelFileTemplate().createFileInDir(event, viewmodelDir)
                SharedViewModelFileTemplate().createFileInDir(event, viewmodelDir)
            }
        }
        createSubDirectory(root, "common") { commonDir ->
            createSubDirectory(commonDir, "nav") { navDir ->
                NavActionFileTemplate().createFileInDir(event, navDir)
            }
            createSubDirectory(commonDir, "helper") { helperDir ->
                ClassNameHelperFileTemplate().createFileInDir(event, helperDir)
            }
        }
        createSubDirectory(root, "app") { appDir ->
            ActivityViewModelFileTemplate().createFileInDir(event, appDir)
            MainActivityFileTemplate(createAppPromptResult.appName).createFileInDir(event, appDir)
            ApplicationFileTemplate(createAppPromptResult.appName).createFileInDir(event, appDir)
            createSubDirectory(appDir, "di") { diDir ->
                InitKoinFileTemplate().createFileInDir(event, diDir)
                KoinModulesFileTemplate().createFileInDir(event, diDir)
            }
            createSubDirectory(appDir, "nav") { navDir ->
                NavManagerFileTemplate().createFileInDir(event, navDir)
            }
            createSubDirectory(appDir, "effect") { effectDir ->
                NavEffectFileTemplate().createFileInDir(event, effectDir)
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
            return event.isUninitializedRootPackageOrDirectChild()
        }
    }
}