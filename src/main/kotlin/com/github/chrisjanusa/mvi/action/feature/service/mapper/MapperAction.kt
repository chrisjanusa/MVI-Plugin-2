package com.github.chrisjanusa.mvi.action.feature.service.mapper

import com.github.chrisjanusa.mvi.helper.file_helper.isInsideServicePackage
import com.github.chrisjanusa.mvi.package_structure.getFeaturePackage
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class MapperAction: AnAction("Create _Mapper") {
    override fun actionPerformed(event: AnActionEvent) {
        val featurePackage = event.getFeaturePackage() ?: return
        val domainModels = featurePackage.domainModels
        val entities = featurePackage.servicePackage?.allEntities ?: emptyList()
        val dtos = featurePackage.servicePackage?.allDTOs ?: emptyList()

        val mapperPromptResult = MapperPromptResult()
        val dialog = MapperDialog(
            mapperPromptResult = mapperPromptResult,
            domainModels = domainModels,
            entities = entities,
            dtos = dtos
        )
        val isCancelled = !dialog.showAndGet()
        if (isCancelled) return
        val to = mapperPromptResult.to
        val from = mapperPromptResult.from
        if (to == null || from == null) return

        featurePackage.createServicePackage()?.addMapper(from, to)
    }

    override fun update(event: AnActionEvent) {
        event.presentation.isEnabledAndVisible = isEnabled(event)
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

    companion object {
        fun isEnabled(event: AnActionEvent): Boolean {
            return event.isInsideServicePackage()
        }
    }
}