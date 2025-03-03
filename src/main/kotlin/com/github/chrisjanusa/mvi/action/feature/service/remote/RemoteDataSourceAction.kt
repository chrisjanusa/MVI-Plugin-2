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

        featurePackage.rootPackage.commonPackage?.servicePackage?.createData()
        featurePackage.rootPackage.commonPackage?.servicePackage?.createRemoteHelpers()
        featurePackage.projectPackage?.libraryManager?.addKoin()
        featurePackage.projectPackage?.libraryManager?.addKtor()

        val dataSourcePackage = featurePackage.createServicePackage()?.createRemoteDataSource(remotePromptResult.name.toPascalCase(), remotePromptResult.baseUrl, remotePromptResult.endpoint)
        val koinModule = featurePackage.rootPackage.koinModule
        dataSourcePackage?.dataSource?.let { koinModule?.addRemoteDataSource(it) }
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