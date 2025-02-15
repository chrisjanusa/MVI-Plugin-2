package com.github.chrisjanusa.kmpmvi.file_managment

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.vfs.VirtualFile

fun VirtualFile.isInCommonCode(): Boolean {
    return findCommonPackage() != null
}

fun AnActionEvent.isInCommonCode(): Boolean {
    val currFile = getData(PlatformDataKeys.VIRTUAL_FILE) ?: return false
    return currFile.isInCommonCode()
}

fun VirtualFile.findCommonPackage(): VirtualFile? {
    var currFile = this
    while (currFile != null && currFile.name != "commonMain") {
        currFile = currFile.parent
    }
    return currFile
}