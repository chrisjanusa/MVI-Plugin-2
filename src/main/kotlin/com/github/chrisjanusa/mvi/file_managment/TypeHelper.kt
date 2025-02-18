package com.github.chrisjanusa.mvi.file_managment

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiManager

fun AnActionEvent.getDirFromFile(rootFile: VirtualFile?): PsiDirectory? {
    val rootPackage = rootFile ?: return null
    val project = getData(PlatformDataKeys.PROJECT) ?: return null
    return PsiManager.getInstance(project).findDirectory(rootPackage)
}