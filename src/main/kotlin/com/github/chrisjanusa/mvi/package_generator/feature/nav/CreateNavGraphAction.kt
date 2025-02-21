package com.github.chrisjanusa.mvi.package_generator.feature.nav

import com.github.chrisjanusa.mvi.helper.file_managment.createSubDirectory
import com.github.chrisjanusa.mvi.helper.file_managment.getDirectory
import com.github.chrisjanusa.mvi.helper.file_managment.getFeaturePackageFile
import com.github.chrisjanusa.mvi.helper.file_managment.isFeaturePackageOrDirectChild
import com.github.chrisjanusa.mvi.helper.file_managment.toPascalCase
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class CreateNavGraphAction : AnAction("Create _Nav Graph") {
    override fun actionPerformed(event: AnActionEvent) {
        val featureFile = event.getFeaturePackageFile() ?: return
        featureFile.getDirectory(event)?.createSubDirectory("nav") { navDir ->
            NavGraphFileTemplate(event, featureFile.name).createFileInDir(navDir)
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
            if (!event.isFeaturePackageOrDirectChild()) return false
            val featureFile = event.getFeaturePackageFile() ?: return false
            val navGraphName = "${featureFile.name.toPascalCase()}NavGraph.kt"
            return featureFile.findChild("nav")?.findChild(navGraphName) == null
        }
    }
}