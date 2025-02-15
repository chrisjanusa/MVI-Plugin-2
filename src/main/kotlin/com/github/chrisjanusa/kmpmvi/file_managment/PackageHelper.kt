package com.github.chrisjanusa.kmpmvi.file_managment

import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory

fun String.pathToPackage(): String {
    return this.substringAfter("commonMain/").substringAfter("/").replace('/', '.')
}

fun PsiDirectory.getPackage() = virtualFile.getPackage()

fun VirtualFile.getPackage(): String {
    return path.pathToPackage()
}

