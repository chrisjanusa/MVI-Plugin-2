package com.github.chrisjanusa.mvi.file_managment

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory

fun String.pathToPackage(): String {
    return this.substringAfter("main/").substringAfter("/").replace('/', '.')
}

fun PsiDirectory.getPackage() = virtualFile.getPackage()

fun VirtualFile.getPackage(): String {
    return path.pathToPackage()
}

fun AnActionEvent.isPackageOrDirectChild(targetPackage: VirtualFile?): Boolean {
    targetPackage ?: return false
    val selectedFile = getData(PlatformDataKeys.VIRTUAL_FILE) ?: return false
    return selectedFile == targetPackage || targetPackage.children.contains(selectedFile)
}

