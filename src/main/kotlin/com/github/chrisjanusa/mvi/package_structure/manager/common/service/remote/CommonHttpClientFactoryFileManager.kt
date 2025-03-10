package com.github.chrisjanusa.mvi.package_structure.manager.common.service.remote

import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.parent_provider.CommonChild
import com.intellij.openapi.vfs.VirtualFile

open class CommonHttpClientFactoryFileManager(file: VirtualFile) : FileManager(file), CommonChild {
    val remotePackage by lazy {
        CommonRemotePackage(file.parent)
    }
    val servicePackage by lazy {
        remotePackage.servicePackage
    }
    override val commonPackage by lazy {
        remotePackage.commonPackage
    }
    override val rootPackage by lazy {
        remotePackage.rootPackage
    }

    companion object : StaticChildInstanceCompanion("HttpClientFactory", CommonRemotePackage) {
        override fun createInstance(virtualFile: VirtualFile) = CommonHttpClientFactoryFileManager(virtualFile)
        fun createNewInstance(insertionPackage: CommonRemotePackage): CommonHttpClientFactoryFileManager? {
            return insertionPackage.createNewFile(
                NAME,
                CommonHttpClientFactoryTemplate(insertionPackage, NAME)
                    .createContent()
            )?.let { CommonHttpClientFactoryFileManager(it) }
        }
    }
}