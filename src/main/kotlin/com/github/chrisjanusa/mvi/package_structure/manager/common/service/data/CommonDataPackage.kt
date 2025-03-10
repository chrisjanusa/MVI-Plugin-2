package com.github.chrisjanusa.mvi.package_structure.manager.common.service.data

import com.github.chrisjanusa.mvi.helper.file_helper.findChildFile
import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.common.service.CommonServicePackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.CommonChild
import com.intellij.openapi.vfs.VirtualFile

class CommonDataPackage(file: VirtualFile) : PackageManager(file), CommonChild {
    val servicePackage by lazy {
        CommonServicePackage(file.parent)
    }
    override val commonPackage by lazy {
        servicePackage.commonPackage
    }
    override val rootPackage by lazy {
        servicePackage.rootPackage
    }
    val result by lazy {
        file.findChildFile(CommonServiceResultFileManager.NAME)?.let { CommonServiceResultFileManager(it) }
    }
    val dataError by lazy {
        file.findChildFile(CommonServiceDataErrorFileManager.NAME)?.let { CommonServiceDataErrorFileManager(it) }
    }
    val error by lazy {
        file.findChildFile(CommonServiceErrorFileManager.NAME)?.let { CommonServiceErrorFileManager(it) }
    }

    private fun createAllChildren() {
        CommonServiceResultFileManager.createNewInstance(this)
        CommonServiceDataErrorFileManager.createNewInstance(this)
        CommonServiceErrorFileManager.createNewInstance(this)
    }

    companion object : StaticChildInstanceCompanion("data", CommonServicePackage) {
        override fun createInstance(virtualFile: VirtualFile) = CommonDataPackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(CommonServiceResultFileManager, CommonServiceDataErrorFileManager, CommonServiceErrorFileManager)
        fun createNewInstance(insertionPackage: CommonServicePackage): CommonDataPackage? {
            val dataPackage = insertionPackage.createNewDirectory(NAME)?.let { CommonDataPackage(it) }
            dataPackage?.createAllChildren()
            return dataPackage
        }
    }
}