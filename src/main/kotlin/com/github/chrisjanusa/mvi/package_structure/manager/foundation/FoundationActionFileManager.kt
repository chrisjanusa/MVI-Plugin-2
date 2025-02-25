package com.github.chrisjanusa.mvi.package_structure.manager.foundation

import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootChild
import com.intellij.openapi.vfs.VirtualFile

open class FoundationActionFileManager(file: VirtualFile) : FileManager(file), RootChild {
    val foundationPackage by lazy {
        FoundationPackage(file.parent)
    }
    override val rootPackage by lazy {
        foundationPackage.rootPackage
    }

    companion object : StaticChildInstanceCompanion("Action", FoundationPackage) {
        override fun createInstance(virtualFile: VirtualFile) = FoundationActionFileManager(virtualFile)
        fun createNewInstance(insertionPackage: FoundationPackage): FoundationActionFileManager? {
            return insertionPackage.createNewFile(
                NAME,
                FoundationActionTemplate(insertionPackage, NAME)
                    .createContent()
            )?.let { FoundationActionFileManager(it) }
        }
    }
}