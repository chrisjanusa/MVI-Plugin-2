package com.github.chrisjanusa.mvi.package_structure.manager.common.service.remote

import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootChild
import com.intellij.openapi.vfs.VirtualFile

open class CommonHttpClientHelperFileManager(file: VirtualFile) : FileManager(file), RootChild {
    val remotePackage by lazy {
        CommonRemotePackage(file.parent)
    }
    val servicePackage by lazy {
        remotePackage.servicePackage
    }
    val commonPackage by lazy {
        remotePackage.commonPackage
    }
    override val rootPackage by lazy {
        remotePackage.rootPackage
    }

    companion object : StaticChildInstanceCompanion("HttpClientHelper", CommonRemotePackage) {
        override fun createInstance(virtualFile: VirtualFile) = CommonHttpClientHelperFileManager(virtualFile)
        fun createNewInstance(insertionPackage: CommonRemotePackage): CommonHttpClientHelperFileManager? {
            return insertionPackage.createNewFile(
                NAME,
                CommonHttpClientHelperTemplate(insertionPackage, NAME)
                    .createContent()
            )?.let { CommonHttpClientHelperFileManager(it) }
        }
    }
}