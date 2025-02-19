package com.github.chrisjanusa.mvi.file_managment

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory

fun AnActionEvent.isRootPackageOrDirectChild(): Boolean {
    return isPackageOrDirectChild(getRootPackageFile())
}

fun AnActionEvent.getRootPackageFile(): VirtualFile? {
    return findParentOfFile("foundation")
}

fun AnActionEvent.isUninitializedRootPackageOrDirectChild(): Boolean {
    return isPackageOrDirectChild(getUninitializedRootPackageFile())
}

fun AnActionEvent.getUninitializedRootPackageFile(): VirtualFile? {
    return findParentOfFile("MainActivity.kt", "app")
}

fun AnActionEvent.getRootDir(): PsiDirectory? {
    return getRootPackageFile().getDirectory(this)
}

fun AnActionEvent.getUninitializedRootDir(): PsiDirectory? {
    return getUninitializedRootPackageFile().getDirectory(this)
}

fun AnActionEvent.getRootPackage(): String =
    getRootPackageFile()?.getPackage() ?: "Could not find Root Package"
