package com.github.chrisjanusa.kmpmvi.file_managment

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiManager

fun AnActionEvent.isRootPackageOrDirectChild(): Boolean {
    val rootPackage = getRootPackageFile() ?: return false
    val selectedFile = getData(PlatformDataKeys.VIRTUAL_FILE) ?: return false
    return selectedFile == rootPackage || rootPackage.children.contains(selectedFile)
}

fun AnActionEvent.getRootPackageFile(): VirtualFile? {
    var currFile = getData(PlatformDataKeys.VIRTUAL_FILE) ?: return null
    while (currFile != null && currFile.findChild("app") == null) {
        currFile = currFile.parent
    }
    return currFile
}

fun AnActionEvent.getRootDir(): PsiDirectory? {
    val rootPackage = getRootPackageFile() ?: return null
    val project = getData(PlatformDataKeys.PROJECT) ?: return null
    return PsiManager.getInstance(project).findDirectory(rootPackage)
}

fun AnActionEvent.getRootPackage(): String =
    getRootPackageFile()?.getPackage() ?: "Could not find Root Package"
