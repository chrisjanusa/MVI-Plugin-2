package com.github.chrisjanusa.mvi.file_managment

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.vfs.VirtualFile

fun AnActionEvent.isInsideFeaturePackage(): Boolean {
    return getFeaturePackageFile() != null
}

fun AnActionEvent.isFeaturePackageOrDirectChild(): Boolean {
    return isPackageOrDirectChild(getFeaturePackageFile())
}

fun AnActionEvent.getFeaturePackageFile(): VirtualFile? {
    return findParentOfFile("plugin")
}
