package com.github.chrisjanusa.mvi.file_managment

import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFileFactory
import com.intellij.util.ThrowableRunnable

fun createSubDirectory(parentDirectory: PsiDirectory, name: String, afterDirectoryCreated: (PsiDirectory) -> Unit = {}) {
    if (parentDirectory.findSubdirectory(name) == null) {
        WriteAction.run(ThrowableRunnable {
            afterDirectoryCreated(parentDirectory.createSubdirectory(name))
        })
    }
}

fun createFileInDirectory(project: Project, directory: PsiDirectory, fileName: String, content: String) {
    WriteAction.run(ThrowableRunnable {
        val file =
            PsiFileFactory.getInstance(project).createFileFromText(
                fileName,
                KotlinFileType,
                content
            )
        directory.add(file)
    })
}