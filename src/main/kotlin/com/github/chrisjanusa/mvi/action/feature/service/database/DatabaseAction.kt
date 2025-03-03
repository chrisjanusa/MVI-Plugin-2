package com.github.chrisjanusa.mvi.action.feature.service.database

import com.github.chrisjanusa.mvi.helper.file_helper.isInsideFeaturePackage
import com.github.chrisjanusa.mvi.package_structure.getFeaturePackage
import com.github.chrisjanusa.mvi.package_structure.manager.project.library.librarygroup.addRoom
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class DatabaseAction : AnAction("Create _Database") {
    override fun actionPerformed(event: AnActionEvent) {
        val featurePackage = event.getFeaturePackage() ?: return
        val featureName = featurePackage.featureName

        val databasePromptResult = DatabasePromptResult(name = featureName)
        val dialog = DatabaseDialog(databasePromptResult)
        val isCancelled = !dialog.showAndGet()
        if (isCancelled) return

        featurePackage.rootPackage.commonPackage?.servicePackage?.createData()
        val libraryManager = featurePackage.projectPackage?.libraryManager
        libraryManager?.addRoom()
        libraryManager?.writeToGradle()

        val databasePackage = featurePackage.createServicePackage()?.createDatabase(databasePromptResult.name, databasePromptResult.entityNames)
        val koinModule = featurePackage.rootPackage.koinModule
        databasePackage?.database?.let {
            koinModule?.addDatabase(it)
            koinModule?.writeToDisk()
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