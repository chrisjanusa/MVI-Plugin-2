package com.github.chrisjanusa.mvi.package_structure.manager.feature.service.remote

import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.service.ServicePackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.ServiceChild
import com.intellij.openapi.vfs.VirtualFile

class FeatureRemoteWrapperPackage(file: VirtualFile): PackageManager(file), ServiceChild {
    val dataSources by lazy {
        file.children.map { FeatureRemotePackage(it) }
    }
    val allDTOs by lazy {
        dataSources.flatMap { it.dtos }
    }
    override val servicePackage by lazy {
        ServicePackage(file.parent)
    }
    val featureName by lazy {
        servicePackage.featureName
    }
    override val featurePackage by lazy {
        servicePackage.featurePackage
    }
    override val rootPackage by lazy {
        servicePackage.rootPackage
    }

    fun createDataSource(dataSourceName: String, baseUrl: String, endpoint: String): FeatureRemotePackage? {
        return FeatureRemotePackage.createNewInstance(
            insertionPackage = this,
            dataSourceName = dataSourceName,
            baseUrl = baseUrl,
            endpoint = endpoint,
        )
    }

    companion object : StaticChildInstanceCompanion("remote", ServicePackage) {
        override fun createInstance(virtualFile: VirtualFile) = FeatureRemoteWrapperPackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(FeatureRemotePackage)

        fun createNewInstance(insertionPackage: ServicePackage): FeatureRemoteWrapperPackage? {
            return insertionPackage.createNewDirectory(NAME)?.let { FeatureRemoteWrapperPackage(it) }
        }
    }
}