package com.github.chrisjanusa.mvi.package_structure.manager.feature.service.remote

import com.github.chrisjanusa.mvi.helper.file_helper.findChildFile
import com.github.chrisjanusa.mvi.helper.file_helper.toPascalCase
import com.github.chrisjanusa.mvi.package_structure.instance_companion.ChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.parent_provider.ServiceChild
import com.intellij.openapi.vfs.VirtualFile

class FeatureRemotePackage(file: VirtualFile): PackageManager(file), ServiceChild {
    val dataSource by lazy {
        file.findChildFile(RemoteDataSourceFileManager.getFileName(dataSourceName))?.let { RemoteDataSourceFileManager(it) }
    }
    val dtos by lazy {
        file.children.mapNotNull {
            if (RemoteDtoFileManager.isInstance(it)) {
                RemoteDtoFileManager(it)
            } else {
                null
            }
        }
    }
    val remoteWrapperPackage by lazy {
        FeatureRemoteWrapperPackage(file.parent)
    }
    override val servicePackage by lazy {
        remoteWrapperPackage.servicePackage
    }
    val featureName by lazy {
        remoteWrapperPackage.featureName
    }
    override val featurePackage by lazy {
        remoteWrapperPackage.featurePackage
    }
    override val rootPackage by lazy {
        remoteWrapperPackage.rootPackage
    }
    val dataSourceName by lazy {
        name.toPascalCase()
    }

    private fun createAllChildren(baseUrl: String, endpoint: String) {
        RemoteDataSourceFileManager.createNewInstance(this, baseUrl, endpoint)
        RemoteDtoFileManager.createNewInstance(this, dataSourceName.toPascalCase())
    }

    companion object : ChildInstanceCompanion(FeatureRemoteWrapperPackage) {
        override fun createInstance(virtualFile: VirtualFile) = FeatureRemotePackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(RemoteDataSourceFileManager, RemoteDtoFileManager)

        fun createNewInstance(
            insertionPackage: FeatureRemoteWrapperPackage,
            dataSourceName: String,
            baseUrl: String,
            endpoint: String
        ): FeatureRemotePackage? {
            val dataSourcePackage = insertionPackage.createNewDirectory(dataSourceName)?.let { FeatureRemotePackage(it) }
            dataSourcePackage?.createAllChildren(baseUrl, endpoint)
            return dataSourcePackage
        }
    }
}