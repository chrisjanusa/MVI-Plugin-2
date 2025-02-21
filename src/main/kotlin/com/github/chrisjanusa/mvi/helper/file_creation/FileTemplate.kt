package com.github.chrisjanusa.mvi.helper.file_creation

import com.github.chrisjanusa.mvi.helper.file_managment.createFileInDirectory
import com.github.chrisjanusa.mvi.helper.file_managment.getFeaturePackageFile
import com.github.chrisjanusa.mvi.helper.file_managment.getPackage
import com.github.chrisjanusa.mvi.helper.file_managment.getPluginPackageFile
import com.github.chrisjanusa.mvi.helper.file_managment.getRootPackage
import com.github.chrisjanusa.mvi.helper.file_managment.toPascalCase
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.psi.PsiDirectory

abstract class FileTemplate(
    private val actionEvent: AnActionEvent,
    internal val rootPackage: String = actionEvent.getRootPackage(),
    internal val featurePackage: String? = actionEvent.getFeaturePackageFile()?.name,
    internal val pluginPackage: String? = actionEvent.getPluginPackageFile()?.name,
) {
    internal val featureName = featurePackage?.toPascalCase()
    internal val pluginName = pluginPackage?.toPascalCase()
    abstract val fileName: String

    fun createFileInDir(
        dir: PsiDirectory,
    ) {
        if (dir.findFile("$fileName.kt") != null) return
        val project = actionEvent.getData(PlatformDataKeys.PROJECT) ?: return
        val content = "package ${dir.getPackage()}\n\n${createContent()}"
        dir.createFileInDirectory(project, "$fileName.kt", content)
    }

    abstract fun createContent(): String
}