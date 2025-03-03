package com.github.chrisjanusa.mvi.package_structure.manager.common.service.remote

import com.github.chrisjanusa.mvi.helper.file_helper.findChildFile
import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.common.service.CommonServicePackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootChild
import com.intellij.openapi.vfs.VirtualFile

class CommonRemotePackage(file: VirtualFile) : PackageManager(file), RootChild {
    val servicePackage by lazy {
        CommonServicePackage(file.parent)
    }
    val commonPackage by lazy {
        servicePackage.commonPackage
    }
    override val rootPackage by lazy {
        servicePackage.rootPackage
    }
    val httpClientFactory by lazy {
        file.findChildFile(CommonHttpClientFactoryFileManager.NAME)?.let { CommonHttpClientFactoryFileManager(it) }
    }
    val httpClientHelper by lazy {
        file.findChildFile(CommonHttpClientHelperFileManager.NAME)?.let { CommonHttpClientHelperFileManager(it) }
    }

    private fun createAllChildren() {
        CommonHttpClientFactoryFileManager.createNewInstance(this)
        CommonHttpClientHelperFileManager.createNewInstance(this)
    }

    companion object : StaticChildInstanceCompanion("remote", CommonServicePackage) {
        override fun createInstance(virtualFile: VirtualFile) = CommonRemotePackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(CommonHttpClientFactoryFileManager, CommonHttpClientHelperFileManager)
        fun createNewInstance(insertionPackage: CommonServicePackage): CommonRemotePackage? {
            val dataPackage = insertionPackage.createNewDirectory(NAME)?.let { CommonRemotePackage(it) }
            dataPackage?.createAllChildren()
            return dataPackage
        }
    }
}