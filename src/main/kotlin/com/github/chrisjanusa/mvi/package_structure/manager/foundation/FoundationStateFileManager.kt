package com.github.chrisjanusa.mvi.package_structure.manager.foundation

import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootChild
import com.intellij.openapi.vfs.VirtualFile

open class FoundationStateFileManager(file: VirtualFile) : FileManager(file), RootChild {
    val foundationPackage by lazy {
        FoundationPackage(file.parent)
    }
    override val rootPackage by lazy {
        foundationPackage.rootPackage
    }
    val noStatePackagePath
        get() = "$packagePathExcludingFile.$NO_STATE"

    companion object : StaticChildInstanceCompanion("State", FoundationPackage) {
        override fun createInstance(virtualFile: VirtualFile) = FoundationStateFileManager(virtualFile)
        fun createNewInstance(insertionPackage: FoundationPackage): FoundationStateFileManager? {
            return insertionPackage.createNewFile(
                NAME,
                FoundationStateTemplate(insertionPackage, NAME)
                    .createContent()
            )?.let { FoundationStateFileManager(it) }
        }
        const val NO_STATE = "NoState"
    }
}