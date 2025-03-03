package com.github.chrisjanusa.mvi.package_structure.manager.common.service.data

import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootChild
import com.intellij.openapi.vfs.VirtualFile

open class CommonServiceResultFileManager(file: VirtualFile) : FileManager(file), RootChild {
    val dataPackage by lazy {
        CommonDataPackage(file.parent)
    }
    val servicePackage by lazy {
        dataPackage.servicePackage
    }
    val commonPackage by lazy {
        dataPackage.commonPackage
    }
    override val rootPackage by lazy {
        dataPackage.rootPackage
    }

    companion object : StaticChildInstanceCompanion("Result", CommonDataPackage) {
        override fun createInstance(virtualFile: VirtualFile) = CommonServiceResultFileManager(virtualFile)
        fun createNewInstance(insertionPackage: CommonDataPackage): CommonServiceResultFileManager? {
            return insertionPackage.createNewFile(
                NAME,
                CommonServiceResultTemplate(insertionPackage, NAME)
                    .createContent()
            )?.let { CommonServiceResultFileManager(it) }
        }
    }
}