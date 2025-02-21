package com.github.chrisjanusa.mvi.helper.file_managment

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.vfs.VirtualFile

fun AnActionEvent.isInsidePluginPackage(): Boolean {
    return getFeaturePackageFile() != null
}

fun AnActionEvent.getPluginPackageFile(): VirtualFile? {
    return findChildOfFile("plugin")
}
