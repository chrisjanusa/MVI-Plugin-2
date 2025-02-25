package com.github.chrisjanusa.mvi.package_structure.manager.foundation

import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootChild
import com.intellij.openapi.vfs.VirtualFile

open class FoundationEffectFileManager(file: VirtualFile) : FileManager(file), RootChild {
    val foundationPackage by lazy {
        FoundationPackage(file.parent)
    }
    override val rootPackage by lazy {
        foundationPackage.rootPackage
    }

    companion object : StaticChildInstanceCompanion("Effect", FoundationPackage) {
        override fun createInstance(virtualFile: VirtualFile) = FoundationEffectFileManager(virtualFile)
        fun createNewInstance(insertionPackage: FoundationPackage): FoundationEffectFileManager? {
            return insertionPackage.createNewFile(
                NAME,
                FoundationEffectTemplate(insertionPackage, NAME)
                    .createContent()
            )?.let { FoundationEffectFileManager(it) }
        }
    }
}