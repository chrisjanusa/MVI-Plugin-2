package com.github.chrisjanusa.mvi.helper.file_helper

import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFileFactory
import com.intellij.util.ThrowableRunnable

fun PsiDirectory.createSubDirectory(name: String, afterDirectoryCreated: (PsiDirectory) -> Unit = {}) {
    val existingDir = findSubdirectory(name)
    if (existingDir == null) {
        WriteAction.run(ThrowableRunnable {
            afterDirectoryCreated(createSubdirectory(name))
        })
    } else {
        afterDirectoryCreated(existingDir)
    }
}

fun PsiDirectory.createFileInDirectory(project: Project, fileName: String, content: String) {
    if(findFile(fileName) == null) {
        WriteAction.run(ThrowableRunnable {
            val file =
                PsiFileFactory.getInstance(project).createFileFromText(
                    fileName,
                    KotlinFileType,
                    content
                )
            add(file)
        })
    }
}

fun PsiDirectory.deleteFileInDirectory(fileName: String) {
    WriteAction.run(ThrowableRunnable {
        findFile(fileName)?.delete()
    })
}