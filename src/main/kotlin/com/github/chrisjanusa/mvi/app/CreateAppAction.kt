package com.github.chrisjanusa.mvi.app


import com.github.chrisjanusa.mvi.app.file_templates.ActivityViewModelFileTemplate
import com.github.chrisjanusa.mvi.app.file_templates.ApplicationFileTemplate
import com.github.chrisjanusa.mvi.app.file_templates.InitKoinFileTemplate
import com.github.chrisjanusa.mvi.app.file_templates.KoinModulesFileTemplate
import com.github.chrisjanusa.mvi.app.file_templates.MainActivityFileTemplate
import com.github.chrisjanusa.mvi.app.file_templates.NavEffectFileTemplate
import com.github.chrisjanusa.mvi.app.file_templates.NavManagerFileTemplate
import com.github.chrisjanusa.mvi.app.file_templates.common.ClassNameHelperFileTemplate
import com.github.chrisjanusa.mvi.app.file_templates.common.NavActionFileTemplate
import com.github.chrisjanusa.mvi.app.file_templates.foundation.ActionFileTemplate
import com.github.chrisjanusa.mvi.app.file_templates.foundation.EffectFileTemplate
import com.github.chrisjanusa.mvi.app.file_templates.foundation.PluginFileTemplate
import com.github.chrisjanusa.mvi.app.file_templates.foundation.SliceUpdateFileTemplate
import com.github.chrisjanusa.mvi.app.file_templates.foundation.nav.NavComponentFileTemplate
import com.github.chrisjanusa.mvi.app.file_templates.foundation.nav.NavComponentIdFileTemplate
import com.github.chrisjanusa.mvi.app.file_templates.foundation.state.SliceFileTemplate
import com.github.chrisjanusa.mvi.app.file_templates.foundation.state.StateFileTemplate
import com.github.chrisjanusa.mvi.app.file_templates.foundation.viewmodel.BaseViewModelFileTemplate
import com.github.chrisjanusa.mvi.app.file_templates.foundation.viewmodel.NoSlicePluginViewModelFileTemplate
import com.github.chrisjanusa.mvi.app.file_templates.foundation.viewmodel.ParentViewModelFileTemplate
import com.github.chrisjanusa.mvi.app.file_templates.foundation.viewmodel.PluginViewModelFileTemplate
import com.github.chrisjanusa.mvi.app.file_templates.foundation.viewmodel.SharedViewModelFileTemplate
import com.github.chrisjanusa.mvi.file_managment.capitalize
import com.github.chrisjanusa.mvi.file_managment.createSubDirectory
import com.github.chrisjanusa.mvi.file_managment.deleteFileInDirectory
import com.github.chrisjanusa.mvi.file_managment.getPackage
import com.github.chrisjanusa.mvi.file_managment.getUninitializedRootDir
import com.github.chrisjanusa.mvi.file_managment.getUninitializedRootPackageFile
import com.github.chrisjanusa.mvi.file_managment.isUninitializedRootPackageOrDirectChild
import com.github.chrisjanusa.mvi.library.getLibraryManager
import com.github.chrisjanusa.mvi.library.librarygroup.addCoroutines
import com.github.chrisjanusa.mvi.library.librarygroup.addKoin
import com.github.chrisjanusa.mvi.library.librarygroup.addNavigation
import com.github.chrisjanusa.mvi.library.librarygroup.addSerialization
import com.github.chrisjanusa.mvi.manifest.getManifestManager
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
        val libraryManager = event.getLibraryManager()
        libraryManager?.addKoin()
        libraryManager?.addNavigation()
        libraryManager?.addCoroutines()
        libraryManager?.addSerialization()
        libraryManager?.writeToGradle()
        val manifestManager = event.getManifestManager()
        manifestManager?.addApplication(createAppPromptResult.appName)
        manifestManager?.writeToGradle()
        root.createSubDirectory( "foundation") { foundationDir ->
            ActionFileTemplate().createFileInDir(event, foundationDir, rootPackage)
            EffectFileTemplate().createFileInDir(event, foundationDir, rootPackage)
            PluginFileTemplate().createFileInDir(event, foundationDir, rootPackage)
            SliceUpdateFileTemplate().createFileInDir(event, foundationDir, rootPackage)
            foundationDir.createSubDirectory("nav") { navDir ->
                NavComponentIdFileTemplate().createFileInDir(event, navDir, rootPackage)
                NavComponentFileTemplate().createFileInDir(event, navDir, rootPackage)
            }
            foundationDir.createSubDirectory("state") { stateDir ->
                StateFileTemplate().createFileInDir(event, stateDir, rootPackage)
                SliceFileTemplate().createFileInDir(event, stateDir, rootPackage)
            }
            foundationDir.createSubDirectory("viewmodel") { viewmodelDir ->
                BaseViewModelFileTemplate().createFileInDir(event, viewmodelDir, rootPackage)
                NoSlicePluginViewModelFileTemplate().createFileInDir(event, viewmodelDir, rootPackage)
                ParentViewModelFileTemplate().createFileInDir(event, viewmodelDir, rootPackage)
                PluginViewModelFileTemplate().createFileInDir(event, viewmodelDir, rootPackage)
                SharedViewModelFileTemplate().createFileInDir(event, viewmodelDir, rootPackage)
            }
        }
        root.createSubDirectory("common") { commonDir ->
            commonDir.createSubDirectory("nav") { navDir ->
                NavActionFileTemplate().createFileInDir(event, navDir, rootPackage)
            }
            commonDir.createSubDirectory("helper") { helperDir ->
                ClassNameHelperFileTemplate().createFileInDir(event, helperDir, rootPackage)
            }
        }
        root.createSubDirectory("app") { appDir ->
            ActivityViewModelFileTemplate().createFileInDir(event, appDir, rootPackage)
            MainActivityFileTemplate(createAppPromptResult.appName).createFileInDir(event, appDir, rootPackage)
            ApplicationFileTemplate(createAppPromptResult.appName).createFileInDir(event, appDir, rootPackage)
            appDir.createSubDirectory("di") { diDir ->
                InitKoinFileTemplate().createFileInDir(event, diDir, rootPackage)
                KoinModulesFileTemplate().createFileInDir(event, diDir, rootPackage)
            }
            appDir.createSubDirectory("nav") { navDir ->
                NavManagerFileTemplate().createFileInDir(event, navDir, rootPackage)
            }
            appDir.createSubDirectory("effect") { effectDir ->
                NavEffectFileTemplate().createFileInDir(event, effectDir, rootPackage)
            }
        }
        root.deleteFileInDirectory("MainActivity.kt")
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