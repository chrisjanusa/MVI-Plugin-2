package com.github.chrisjanusa.mvi.file_managment

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory

fun AnActionEvent.isRootPackageOrDirectChild(): Boolean {
    return isPackageOrDirectChild(getRootPackageFile())
}

fun AnActionEvent.getRootPackageFile(): VirtualFile? {
    return findParentOfFile("app")
}

fun AnActionEvent.isUninitializedRootPackageOrDirectChild(): Boolean {
    return isPackageOrDirectChild(getUninitializedRootPackageFile())
}

fun AnActionEvent.getUninitializedRootPackageFile(): VirtualFile? {
    return findParentOfFile("MainActivity.kt")
}

private fun AnActionEvent.findParentOfFile(fileName: String): VirtualFile?  {
    var currFile = getData(PlatformDataKeys.VIRTUAL_FILE) ?: return null
    while (currFile != null && currFile.findChild(fileName) == null) {
        currFile = currFile.parent
    }
    return currFile
}

fun AnActionEvent.getRootDir(): PsiDirectory? {
    return getDirFromFile(getRootPackageFile())
}

fun AnActionEvent.getUninitializedRootDir(): PsiDirectory? {
    return getDirFromFile(getUninitializedRootPackageFile())
}

fun AnActionEvent.getRootPackage(): String =
    getRootPackageFile()?.getPackage() ?: "Could not find Root Package"
