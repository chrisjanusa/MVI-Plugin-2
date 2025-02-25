package com.github.chrisjanusa.mvi.package_structure.manager.foundation

import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootChild
import com.intellij.openapi.vfs.VirtualFile

open class FoundationSliceFileManager(file: VirtualFile) : FileManager(file), RootChild {
    val foundationPackage by lazy {
        FoundationPackage(file.parent)
    }
    override val rootPackage by lazy {
        foundationPackage.rootPackage
    }
    val noSlicePackagePath
        get() = "$packagePathExcludingFile.$NO_SLICE"

    companion object : StaticChildInstanceCompanion("Slice", FoundationPackage) {
        override fun createInstance(virtualFile: VirtualFile) = FoundationSliceFileManager(virtualFile)
        fun createNewInstance(insertionPackage: FoundationPackage): FoundationSliceFileManager? {
            return insertionPackage.createNewFile(
                NAME,
                FoundationSliceTemplate(insertionPackage, NAME)
                    .createContent()
            )?.let { FoundationSliceFileManager(it) }
        }
        const val NO_SLICE = "NoSlice"
    }
}