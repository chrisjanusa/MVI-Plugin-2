package com.github.chrisjanusa.mvi.action.feature.shared

import com.github.chrisjanusa.mvi.helper.file_helper.isFeaturePackageOrDirectChild
import com.github.chrisjanusa.mvi.package_structure.getFeaturePackage
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class CreateSharedStateAction : AnAction("Create _Shared State") {
    override fun actionPerformed(event: AnActionEvent) {
        val featurePackage = event.getFeaturePackage() ?: return
        featurePackage.createSharedState()
    }

    override fun update(event: AnActionEvent) {
        event.presentation.isEnabledAndVisible = isEnabled(event)
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

    companion object {
        fun isEnabled(event: AnActionEvent): Boolean {
            if (!event.isFeaturePackageOrDirectChild()) return false
            val featurePackage = event.getFeaturePackage() ?: return false
            return featurePackage.sharedPackage?.state == null
        }
    }
}