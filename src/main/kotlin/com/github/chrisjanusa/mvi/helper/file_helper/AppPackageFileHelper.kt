package com.github.chrisjanusa.mvi.helper.file_helper

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.vfs.VirtualFile

fun AnActionEvent.getAppPackageFile(): VirtualFile? {
    return getRootPackageFile()?.findChildFile("app")
}
