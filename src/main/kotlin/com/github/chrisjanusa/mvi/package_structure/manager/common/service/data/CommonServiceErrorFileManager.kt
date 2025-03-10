package com.github.chrisjanusa.mvi.package_structure.manager.common.service.data

import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.parent_provider.CommonChild
import com.intellij.openapi.vfs.VirtualFile

open class CommonServiceErrorFileManager(file: VirtualFile) : FileManager(file), CommonChild {
    val dataPackage by lazy {
        CommonDataPackage(file.parent)
    }
    val servicePackage by lazy {
        dataPackage.servicePackage
    }
    override val commonPackage by lazy {
        dataPackage.commonPackage
    }
    override val rootPackage by lazy {
        dataPackage.rootPackage
    }

    companion object : StaticChildInstanceCompanion("Error", CommonDataPackage) {
        override fun createInstance(virtualFile: VirtualFile) = CommonServiceErrorFileManager(virtualFile)
        fun createNewInstance(insertionPackage: CommonDataPackage): CommonServiceErrorFileManager? {
            return insertionPackage.createNewFile(
                NAME,
                CommonServiceErrorTemplate(insertionPackage, NAME)
                    .createContent()
            )?.let { CommonServiceErrorFileManager(it) }
        }
    }
}