package com.github.chrisjanusa.mvi.action.feature.service.remote

import com.github.chrisjanusa.mvi.helper.file_helper.isInsideFeaturePackage
import com.github.chrisjanusa.mvi.helper.file_helper.toPascalCase
import com.github.chrisjanusa.mvi.package_structure.getFeaturePackage
import com.github.chrisjanusa.mvi.package_structure.manager.project.library.librarygroup.addKoin
import com.github.chrisjanusa.mvi.package_structure.manager.project.library.librarygroup.addKtor
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class RemoteDataSourceAction : AnAction("Create Remote Data _Source") {
    override fun actionPerformed(event: AnActionEvent) {
        val featurePackage = event.getFeaturePackage() ?: return
        val featureName = featurePackage.featureName

        val remotePromptResult = RemoteDataSourcePromptResult(name = featureName)
        val dialog = RemoteDataSourceDialog(remotePromptResult)
        val isCancelled = !dialog.showAndGet()
        if (isCancelled) return

        val commonServicePackage = featurePackage.rootPackage.commonPackage?.createServicePackage()
        commonServicePackage?.createData()
        commonServicePackage?.createRemoteHelpers()
        val libraryManager = featurePackage.projectPackage?.libraryManager
        libraryManager?.addKoin()
        libraryManager?.addKtor()
        libraryManager?.writeToGradle()

        val dataSourcePackage = featurePackage.createServicePackage()?.createRemoteDataSource(remotePromptResult.name.toPascalCase(), remotePromptResult.baseUrl, remotePromptResult.endpoint)
        val koinModule = featurePackage.rootPackage.koinModule
        val manifest = featurePackage.rootPackage.projectPackage?.manifest
        manifest?.addPermission("android.permission.INTERNET")
        manifest?.writeToDisk()
        dataSourcePackage?.dataSource?.let {
            koinModule?.addRemoteDataSource(it)
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