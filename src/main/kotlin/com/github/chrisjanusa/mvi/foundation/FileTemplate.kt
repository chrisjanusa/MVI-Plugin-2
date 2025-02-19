package com.github.chrisjanusa.mvi.foundation

import com.github.chrisjanusa.mvi.file_managment.createFileInDirectory
import com.github.chrisjanusa.mvi.file_managment.getPackage
import com.github.chrisjanusa.mvi.file_managment.getRootPackage
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.psi.PsiDirectory

abstract class FileTemplate(internal val fileName: String) {
    fun createFileInDir(actionEvent: AnActionEvent, dir: PsiDirectory, rootPackage: String = actionEvent.getRootPackage()) {
        if (dir.findFile("$fileName.kt") != null) return
        val project = actionEvent.getData(PlatformDataKeys.PROJECT) ?: return
        val content = "package ${dir.getPackage()}\n\n${createContent(rootPackage)}"
        dir.createFileInDirectory(project, "$fileName.kt", content)
    }

    abstract fun createContent(rootPackage: String): String
}