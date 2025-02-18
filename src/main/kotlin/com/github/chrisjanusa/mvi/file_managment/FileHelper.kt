package com.github.chrisjanusa.mvi.file_managment

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.vfs.VirtualFile

internal fun AnActionEvent.findParentOfFile(fileName: String, excludeFileName: String = ""): VirtualFile?  {
    var currFile = getData(PlatformDataKeys.VIRTUAL_FILE) ?: return null
    while (currFile.isValidFile() && (currFile.name == excludeFileName || currFile.findChild(fileName) == null)) {
        currFile = currFile.parent
    }
    return if (currFile.isValidFile()) currFile else null
}

fun VirtualFile.isValidFile() = name != "/"