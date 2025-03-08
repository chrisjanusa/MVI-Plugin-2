package com.github.chrisjanusa.mvi.package_structure.manager.feature.service.repository

import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticSuffixChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.service.FeatureServicePackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.ServiceChild
import com.intellij.openapi.vfs.VirtualFile

class RepositoryFileManager(file: VirtualFile) : FileManager(file), ServiceChild {
    val repositoryPackage by lazy {
        FeatureRepositoryPackage(file)
    }
    override val servicePackage: FeatureServicePackage by lazy {
        repositoryPackage.servicePackage
    }

    override val featurePackage by lazy {
        servicePackage.featurePackage
    }
    val featureName by lazy {
        servicePackage.featureName
    }
    override val rootPackage by lazy {
        servicePackage.rootPackage
    }

    val repositoryName by lazy {
        name.substringBefore(SUFFIX)
    }
    val api by lazy {
        featurePackage.findAPI(repositoryName)
    }

    companion object: StaticSuffixChildInstanceCompanion("Repository", FeatureRepositoryPackage) {
        override fun createInstance(virtualFile: VirtualFile) = RepositoryFileManager(virtualFile)
        fun getFileName(repositoryName: String) = "$repositoryName$SUFFIX"
        fun createNewInstance(insertionPackage: FeatureRepositoryPackage, repositoryName: String): RepositoryFileManager? {
            val fileName = getFileName(repositoryName)
            return insertionPackage.createNewFile(
                fileName,
                RepositoryTemplate(insertionPackage, fileName)
                    .createContent()
            )?.let { RepositoryFileManager(it) }
        }
    }
}