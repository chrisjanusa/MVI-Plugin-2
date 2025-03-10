package com.github.chrisjanusa.mvi.package_structure.manager.common.service.data

import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.parent_provider.CommonChild
import com.intellij.openapi.vfs.VirtualFile

open class CommonServiceDataErrorFileManager(file: VirtualFile) : FileManager(file), CommonChild {
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

    companion object : StaticChildInstanceCompanion("DataError", CommonDataPackage) {
        override fun createInstance(virtualFile: VirtualFile) = CommonServiceDataErrorFileManager(virtualFile)
        fun createNewInstance(insertionPackage: CommonDataPackage): CommonServiceDataErrorFileManager? {
            return insertionPackage.createNewFile(
                NAME,
                CommonServiceDataErrorTemplate(insertionPackage, NAME)
                    .createContent()
            )?.let { CommonServiceDataErrorFileManager(it) }
        }
    }
}