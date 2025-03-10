package com.github.chrisjanusa.mvi.package_structure.manager.common.helper

import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.manager.common.CommonPackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.CommonChild
import com.intellij.openapi.vfs.VirtualFile

open class ClassNameHelperFileManager(file: VirtualFile) : FileManager(file), CommonChild {
    override val commonPackage by lazy {
        CommonPackage(file.parent)
    }
    override val rootPackage by lazy {
        commonPackage.rootPackage
    }
    val functionPackagePath by lazy {
        "$packagePathExcludingFile.getClassName"
    }

    companion object : StaticChildInstanceCompanion("ClassNameHelper", CommonHelperPackage) {
        override fun createInstance(virtualFile: VirtualFile) = ClassNameHelperFileManager(virtualFile)
        fun createNewInstance(insertionPackage: CommonHelperPackage): ClassNameHelperFileManager? {
            return insertionPackage.createNewFile(
                NAME,
                ClassNameHelperTemplate(insertionPackage, NAME)
                    .createContent()
            )?.let { ClassNameHelperFileManager(it) }
        }
    }
}