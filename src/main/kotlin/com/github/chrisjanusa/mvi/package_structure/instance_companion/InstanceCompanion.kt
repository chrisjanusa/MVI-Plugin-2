package com.github.chrisjanusa.mvi.package_structure.instance_companion

import com.github.chrisjanusa.mvi.helper.file_helper.KotlinFileType
import com.github.chrisjanusa.mvi.helper.file_helper.findChildFile
import com.github.chrisjanusa.mvi.package_structure.Manager
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory
import com.intellij.util.ThrowableRunnable

abstract class InstanceCompanion {
    open val allChildrenInstanceCompanions: List<InstanceCompanion>
        get() = emptyList()

    abstract fun isInstance(file: VirtualFile?): Boolean

    abstract fun createInstance(virtualFile: VirtualFile): Manager?

    internal fun PackageManager.createNewFile(name: String, content: String): VirtualFile? {
        val existingFile = file.findChildFile(name)
        if (existingFile != null) return existingFile

        val contentWithPackage = "package ${packagePath}\n\n${content}"
        var file: PsiFile? = null
        WriteAction.runAndWait(ThrowableRunnable {
            file =
                PsiFileFactory.getInstance(project).createFileFromText(
                    "$name.kt",
                    KotlinFileType,
                    contentWithPackage
                )
            file?.let {
                file = directory?.add(it)?.containingFile
            }
        })
        return file?.virtualFile
    }

    internal fun PackageManager.createNewDirectory(name: String): VirtualFile? {
        val existingDirectory = file.findChild(name)
        if (existingDirectory != null) return existingDirectory

        var dirFile: VirtualFile? = null
        WriteAction.runAndWait(ThrowableRunnable {
            dirFile = this.file.createChildDirectory(this, name)
        })
        return dirFile
    }
}