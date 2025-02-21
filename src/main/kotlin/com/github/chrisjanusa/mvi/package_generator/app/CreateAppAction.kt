package com.github.chrisjanusa.mvi.package_generator.app


import com.github.chrisjanusa.mvi.package_generator.app.file_templates.ActivityViewModelFileTemplate
import com.github.chrisjanusa.mvi.package_generator.app.file_templates.ApplicationFileTemplate
import com.github.chrisjanusa.mvi.package_generator.app.file_templates.InitKoinFileTemplate
import com.github.chrisjanusa.mvi.package_generator.app.file_templates.KoinModulesFileTemplate
import com.github.chrisjanusa.mvi.package_generator.app.file_templates.MainActivityFileTemplate
import com.github.chrisjanusa.mvi.package_generator.app.file_templates.NavEffectFileTemplate
import com.github.chrisjanusa.mvi.package_generator.app.file_templates.NavManagerFileTemplate
import com.github.chrisjanusa.mvi.package_generator.app.file_templates.common.ClassNameHelperFileTemplate
import com.github.chrisjanusa.mvi.package_generator.app.file_templates.common.NavActionFileTemplate
import com.github.chrisjanusa.mvi.package_generator.app.file_templates.foundation.ActionFileTemplate
import com.github.chrisjanusa.mvi.package_generator.app.file_templates.foundation.EffectFileTemplate
import com.github.chrisjanusa.mvi.package_generator.app.file_templates.foundation.PluginFileTemplate
import com.github.chrisjanusa.mvi.package_generator.app.file_templates.foundation.SliceUpdateFileTemplate
import com.github.chrisjanusa.mvi.package_generator.app.file_templates.foundation.nav.NavComponentFileTemplate
import com.github.chrisjanusa.mvi.package_generator.app.file_templates.foundation.nav.NavComponentIdFileTemplate
import com.github.chrisjanusa.mvi.package_generator.app.file_templates.foundation.state.SliceFileTemplate
import com.github.chrisjanusa.mvi.package_generator.app.file_templates.foundation.state.StateFileTemplate
import com.github.chrisjanusa.mvi.package_generator.app.file_templates.foundation.viewmodel.BaseViewModelFileTemplate
import com.github.chrisjanusa.mvi.package_generator.app.file_templates.foundation.viewmodel.ParentViewModelFileTemplate
import com.github.chrisjanusa.mvi.package_generator.app.file_templates.foundation.viewmodel.PluginViewModelFileTemplate
import com.github.chrisjanusa.mvi.package_generator.app.file_templates.foundation.viewmodel.SharedViewModelFileTemplate
import com.github.chrisjanusa.mvi.helper.document_management.library.getLibraryManager
import com.github.chrisjanusa.mvi.helper.document_management.library.librarygroup.addCoroutines
import com.github.chrisjanusa.mvi.helper.document_management.library.librarygroup.addKoin
import com.github.chrisjanusa.mvi.helper.document_management.library.librarygroup.addNavigation
import com.github.chrisjanusa.mvi.helper.document_management.library.librarygroup.addSerialization
import com.github.chrisjanusa.mvi.helper.document_management.manifest.getManifestManager
import com.github.chrisjanusa.mvi.helper.file_managment.createSubDirectory
import com.github.chrisjanusa.mvi.helper.file_managment.deleteFileInDirectory
import com.github.chrisjanusa.mvi.helper.file_managment.getPackage
import com.github.chrisjanusa.mvi.helper.file_managment.getUninitializedRootDir
import com.github.chrisjanusa.mvi.helper.file_managment.getUninitializedRootPackageFile
import com.github.chrisjanusa.mvi.helper.file_managment.isUninitializedRootPackageOrDirectChild
import com.github.chrisjanusa.mvi.helper.file_managment.toPascalCase
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class CreateAppAction : AnAction("Initialize _App") {
    override fun actionPerformed(event: AnActionEvent) {
        val rootPackage = event.getUninitializedRootPackageFile()?.getPackage() ?: ""
        val initialAppName = rootPackage.substringAfterLast(".").toPascalCase()
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
        val appName = createAppPromptResult.appName.toPascalCase()
        manifestManager?.addApplication(appName)
        manifestManager?.writeToDisk()
        root.createSubDirectory( "foundation") { foundationDir ->
            ActionFileTemplate(event, rootPackage).createFileInDir(foundationDir)
            EffectFileTemplate(event, rootPackage).createFileInDir(foundationDir)
            PluginFileTemplate(event, rootPackage).createFileInDir(foundationDir)
            SliceUpdateFileTemplate(event, rootPackage).createFileInDir(foundationDir)
            foundationDir.createSubDirectory("nav") { navDir ->
                NavComponentIdFileTemplate(event, rootPackage).createFileInDir(navDir)
                NavComponentFileTemplate(event, rootPackage).createFileInDir(navDir)
            }
            foundationDir.createSubDirectory("state") { stateDir ->
                StateFileTemplate(event, rootPackage).createFileInDir(stateDir)
                SliceFileTemplate(event, rootPackage).createFileInDir(stateDir)
            }
            foundationDir.createSubDirectory("viewmodel") { viewmodelDir ->
                BaseViewModelFileTemplate(event, rootPackage).createFileInDir(viewmodelDir)
                ParentViewModelFileTemplate(event, rootPackage).createFileInDir(viewmodelDir)
                PluginViewModelFileTemplate(event, rootPackage).createFileInDir(viewmodelDir)
                SharedViewModelFileTemplate(event, rootPackage).createFileInDir(viewmodelDir)
            }
        }
        root.createSubDirectory("common") { commonDir ->
            commonDir.createSubDirectory("nav") { navDir ->
                NavActionFileTemplate(event, rootPackage).createFileInDir(navDir)
            }
            commonDir.createSubDirectory("helper") { helperDir ->
                ClassNameHelperFileTemplate(event, rootPackage).createFileInDir(helperDir)
            }
        }
        root.createSubDirectory("app") { appDir ->
            ActivityViewModelFileTemplate(event, rootPackage).createFileInDir(appDir)
            MainActivityFileTemplate(appName, event, rootPackage).createFileInDir(appDir)
            ApplicationFileTemplate(appName, event, rootPackage).createFileInDir(appDir)
            appDir.createSubDirectory("di") { diDir ->
                InitKoinFileTemplate(event, rootPackage).createFileInDir(diDir)
                KoinModulesFileTemplate(event, rootPackage).createFileInDir(diDir)
            }
            appDir.createSubDirectory("nav") { navDir ->
                NavManagerFileTemplate(event, rootPackage).createFileInDir(navDir)
            }
            appDir.createSubDirectory("effect") { effectDir ->
                NavEffectFileTemplate(event, rootPackage).createFileInDir(effectDir)
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