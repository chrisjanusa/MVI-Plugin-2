package com.github.chrisjanusa.mvi.package_structure.manager.feature.service.repository

import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.service.FeatureServicePackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.ServiceChild
import com.intellij.openapi.vfs.VirtualFile

class FeatureRepositoryPackage(file: VirtualFile): PackageManager(file), ServiceChild {
    override val servicePackage by lazy {
        FeatureServicePackage(file.parent)
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

    val repositories by lazy {
        file.children.map {
            RepositoryFileManager(it)
        }
    }

    fun createRepository(repositoryName: String) = RepositoryFileManager.createNewInstance(this, repositoryName)

    companion object : StaticInstanceCompanion("repository") {
        override fun createInstance(virtualFile: VirtualFile) = FeatureRepositoryPackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(RepositoryFileManager)

        fun createNewInstance(insertionPackage: FeatureServicePackage): FeatureRepositoryPackage? {
            return insertionPackage.createNewDirectory(NAME)?.let { FeatureRepositoryPackage(it) }
        }
    }
}