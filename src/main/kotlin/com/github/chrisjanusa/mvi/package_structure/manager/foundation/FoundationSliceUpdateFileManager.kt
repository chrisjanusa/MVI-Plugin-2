package com.github.chrisjanusa.mvi.package_structure.manager.foundation

import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootChild
import com.intellij.openapi.vfs.VirtualFile

open class FoundationSliceUpdateFileManager(file: VirtualFile) : FileManager(file), RootChild {
    val foundationPackage by lazy {
        FoundationPackage(file.parent)
    }
    override val rootPackage by lazy {
        foundationPackage.rootPackage
    }

    companion object : StaticChildInstanceCompanion("SliceUpdate", FoundationPackage) {
        override fun createInstance(virtualFile: VirtualFile) = FoundationSliceUpdateFileManager(virtualFile)
        fun createNewInstance(insertionPackage: FoundationPackage): FoundationSliceUpdateFileManager? {
            return insertionPackage.createNewFile(
                NAME,
                FoundationSliceUpdateTemplate(insertionPackage, NAME)
                    .createContent()
            )?.let { FoundationSliceUpdateFileManager(it) }
        }
    }
}