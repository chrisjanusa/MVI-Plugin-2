package com.github.chrisjanusa.kmpmvi.foundation

import com.github.chrisjanusa.kmpmvi.file_managment.*
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.psi.PsiDirectory

abstract class FileTemplate(private val fileName: String) {
    fun createFileInDir(actionEvent: AnActionEvent, dir: PsiDirectory) {
        if (dir.findFile("$fileName.kt") != null) return
        val project = actionEvent.getData(PlatformDataKeys.PROJECT) ?: return
        val content = "package ${dir.getPackage()}\n\n${createContent(actionEvent.getRootPackage())}"
        createFileInDirectory(project, dir, "$fileName.kt", content)
    }

    abstract fun createContent(rootPackage: String): String
}