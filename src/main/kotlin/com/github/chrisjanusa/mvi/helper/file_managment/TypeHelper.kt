package com.github.chrisjanusa.mvi.helper.file_managment

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiManager

fun VirtualFile?.getDirectory(event: AnActionEvent): PsiDirectory? {
    this ?: return null
    val project = event.getData(PlatformDataKeys.PROJECT) ?: return null
    return PsiManager.getInstance(project).findDirectory(this)
}

fun VirtualFile?.getDocument(): Document? =
    this?.let { FileDocumentManager.getInstance().getDocument(it) }