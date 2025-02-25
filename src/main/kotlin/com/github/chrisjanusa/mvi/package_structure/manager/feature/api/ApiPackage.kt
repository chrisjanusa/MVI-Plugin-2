package com.github.chrisjanusa.mvi.package_structure.manager.feature.api

import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.FeaturePackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.FeatureDirectChild
import com.intellij.openapi.vfs.VirtualFile

class ApiPackage(file: VirtualFile): PackageManager(file), FeatureDirectChild {
    fun findAPI(repositoryName: String): ApiFileManager? {
        for (api in apis) {
            if (api.repositoryName == repositoryName) {
                return api
            }
        }
        return null
    }

    override val featurePackage by lazy {
        FeaturePackage(file.parent)
    }
    val featureName by lazy {
        featurePackage.featureName
    }
    override val rootPackage by lazy {
        featurePackage.rootPackage
    }

    val apis by lazy {
        file.children.map {
            ApiFileManager(it)
        }
    }

    fun createRepository(repositoryName: String) = ApiFileManager.createNewInstance(this, repositoryName)

    companion object : StaticInstanceCompanion("api") {
        override fun createInstance(virtualFile: VirtualFile) = ApiPackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(ApiFileManager)

        fun createNewInstance(insertionPackage: FeaturePackage): ApiPackage? {
            return insertionPackage.createNewDirectory(NAME)?.let { ApiPackage(it) }
        }
    }
}