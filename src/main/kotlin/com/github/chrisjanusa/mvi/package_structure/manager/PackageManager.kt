package com.github.chrisjanusa.mvi.package_structure.manager

import com.github.chrisjanusa.mvi.helper.file_helper.getProject
import com.github.chrisjanusa.mvi.helper.file_helper.getProjectPackage
import com.github.chrisjanusa.mvi.helper.file_helper.pathToPackage
import com.github.chrisjanusa.mvi.package_structure.Manager
import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager
import com.intellij.util.ThrowableRunnable

abstract class PackageManager(val file: VirtualFile): Manager {
    val packagePath = file.path.pathToPackage()
    val name = file.name

    val project by lazy {
        file.getProject()
    }
    open val projectPackage by lazy {
        project?.getProjectPackage()
    }
    val directory by lazy {
        project?.let { PsiManager.getInstance(it).findDirectory(file) }
    }

    fun deleteFile(fileName: String) {
        WriteAction.run(ThrowableRunnable {
            directory?.findFile(fileName)?.delete()
        })
    }

    override fun toString(): String {
        return name
    }
}