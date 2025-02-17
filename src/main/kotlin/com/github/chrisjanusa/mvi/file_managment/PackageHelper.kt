package com.github.chrisjanusa.mvi.file_managment

import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory

fun String.pathToPackage(): String {
    return this.substringAfter("main/").substringAfter("/").replace('/', '.')
}

fun PsiDirectory.getPackage() = virtualFile.getPackage()

fun VirtualFile.getPackage(): String {
    return path.pathToPackage()
}

