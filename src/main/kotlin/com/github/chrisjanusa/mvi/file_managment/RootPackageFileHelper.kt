package com.github.chrisjanusa.mvi.file_managment

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiManager

fun AnActionEvent.isRootPackageOrDirectChild(): Boolean {
    return isRootPackageOrDirectChildGivenRoot(getRootPackageFile())
}

private fun AnActionEvent.isRootPackageOrDirectChildGivenRoot(rootFile: VirtualFile?): Boolean {
    val rootPackage = rootFile ?: return false
    val selectedFile = getData(PlatformDataKeys.VIRTUAL_FILE) ?: return false
    return selectedFile == rootPackage || rootPackage.children.contains(selectedFile)
}

fun AnActionEvent.getRootPackageFile(): VirtualFile? {
    return findParentOfFile("app")
}

fun AnActionEvent.isUninitializedRootPackageOrDirectChild(): Boolean {
    return isRootPackageOrDirectChildGivenRoot(getUninitializedRootPackageFile())
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
    return getRootDirFromFile(getRootPackageFile())
}

fun AnActionEvent.getUninitializedRootDir(): PsiDirectory? {
    return getRootDirFromFile(getUninitializedRootPackageFile())
}

private fun AnActionEvent.getRootDirFromFile(rootFile: VirtualFile?): PsiDirectory? {
    val rootPackage = rootFile ?: return null
    val project = getData(PlatformDataKeys.PROJECT) ?: return null
    return PsiManager.getInstance(project).findDirectory(rootPackage)
}

fun AnActionEvent.getRootPackage(): String =
    getRootPackageFile()?.getPackage() ?: "Could not find Root Package"
