package com.github.chrisjanusa.mvi.package_structure.manager.feature.service.repository

import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticSuffixChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.service.ServicePackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.FeatureChild
import com.intellij.openapi.vfs.VirtualFile

class RepositoryFileManager(file: VirtualFile) : FileManager(file), FeatureChild {
    val repositoryPackage by lazy {
        FeatureRepositoryPackage(file)
    }
    val servicePackage: ServicePackage by lazy {
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
            insertionPackage.rootPackage.koinModule?.addRepository(repositoryName)
            return insertionPackage.createNewFile(
                fileName,
                RepositoryTemplate(insertionPackage, fileName)
                    .createContent()
            )?.let { RepositoryFileManager(it) }
        }
    }
}