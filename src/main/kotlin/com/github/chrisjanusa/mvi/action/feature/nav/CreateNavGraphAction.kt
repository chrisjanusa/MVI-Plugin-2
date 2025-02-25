package com.github.chrisjanusa.mvi.action.feature.nav

import com.github.chrisjanusa.mvi.helper.file_helper.isFeaturePackageOrDirectChild
import com.github.chrisjanusa.mvi.package_structure.getFeaturePackage
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class CreateNavGraphAction : AnAction("Create _Nav Graph") {
    override fun actionPerformed(event: AnActionEvent) {
        val featurePackage = event.getFeaturePackage() ?: return
        featurePackage.createNavGraph()
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
            return featurePackage.navPackage == null
        }
    }
}