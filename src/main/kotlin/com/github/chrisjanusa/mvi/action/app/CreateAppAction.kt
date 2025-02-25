package com.github.chrisjanusa.mvi.action.app


import com.github.chrisjanusa.mvi.helper.file_helper.toPascalCase
import com.github.chrisjanusa.mvi.package_structure.manager.project.library.librarygroup.addSerialization
import com.github.chrisjanusa.mvi.package_structure.getManagerOfType
import com.github.chrisjanusa.mvi.package_structure.getRootPackage
import com.github.chrisjanusa.mvi.package_structure.manager.RootPackage
import com.github.chrisjanusa.mvi.package_structure.manager.project.library.librarygroup.addCoroutines
import com.github.chrisjanusa.mvi.package_structure.manager.project.library.librarygroup.addKoin
import com.github.chrisjanusa.mvi.package_structure.manager.project.library.librarygroup.addNavigation
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class CreateAppAction : AnAction("Initialize _App") {
    override fun actionPerformed(event: AnActionEvent) {
        val rootPackage = event.getManagerOfType(RootPackage) as? RootPackage ?: return
        val rootPackagePath = rootPackage.packagePath
        val initialAppName = rootPackagePath.substringAfterLast(".").toPascalCase()
        val createAppPromptResult = CreateAppPromptResult(appName = initialAppName)
        val dialog = CreateAppDialog(createAppPromptResult)
        val isCancelled = !dialog.showAndGet()
        if (isCancelled) return
        val projectPackage = rootPackage.projectPackage

        val libraryManager = projectPackage?.libraryManager
        libraryManager?.addKoin()
        libraryManager?.addNavigation()
        libraryManager?.addCoroutines()
        libraryManager?.addSerialization()
        libraryManager?.writeToGradle()

        val manifestManager = projectPackage?.manifest
        val appName = createAppPromptResult.appName.toPascalCase()
        manifestManager?.addApplication(appName)
        manifestManager?.writeToDisk()

        rootPackage.createAllChildren(appName)
        rootPackage.deleteFile("MainActivity.kt")
    }

    override fun update(event: AnActionEvent) {
        event.presentation.isEnabledAndVisible = isEnabled(event)
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

    companion object {
        fun isEnabled(event: AnActionEvent): Boolean {
            val rootPackage = event.getRootPackage() ?: return false
            return !rootPackage.isInitialized
        }
    }
}