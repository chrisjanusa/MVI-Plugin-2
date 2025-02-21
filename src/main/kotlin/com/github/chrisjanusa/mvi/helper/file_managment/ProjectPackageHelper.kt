package com.github.chrisjanusa.mvi.helper.file_managment

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile

fun AnActionEvent.getProjectDirFile() : VirtualFile? {
    val basePath = getData(PlatformDataKeys.PROJECT)?.basePath
    if (basePath.isNullOrBlank()) {
        return null
    }
    return LocalFileSystem.getInstance().findFileByPath(basePath)
}