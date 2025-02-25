package com.github.chrisjanusa.mvi.package_structure.manager.feature.service

import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.FeaturePackage
import com.github.chrisjanusa.mvi.package_structure.manager.feature.service.database.FeatureDatabasePackage
import com.github.chrisjanusa.mvi.package_structure.manager.feature.service.mapper.FeatureMapperPackage
import com.github.chrisjanusa.mvi.package_structure.manager.feature.service.remote.FeatureRemotePackage
import com.github.chrisjanusa.mvi.package_structure.manager.feature.service.repository.FeatureRepositoryPackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.FeatureDirectChild
import com.intellij.openapi.vfs.VirtualFile

class ServicePackage(file: VirtualFile): PackageManager(file), FeatureDirectChild {
    val databasePackage by lazy {
        file.findChild(FeatureDatabasePackage.NAME)?.let { FeatureDatabasePackage(it) }
    }

    val repositoryPackage by lazy {
        file.findChild(FeatureRepositoryPackage.NAME)?.let { FeatureRepositoryPackage(it) }
    }
    val repositories by lazy {
        repositoryPackage?.repositories ?: emptyList()
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
    fun createRepositoryPackage() = FeatureRepositoryPackage.createNewInstance(this)

    companion object : StaticInstanceCompanion("service") {
        override fun createInstance(virtualFile: VirtualFile) = ServicePackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(FeatureDatabasePackage, FeatureRepositoryPackage, FeatureRemotePackage, FeatureMapperPackage)

        fun createNewInstance(insertionPackage: FeaturePackage): ServicePackage? {
            return insertionPackage.createNewDirectory(NAME)?.let { ServicePackage(it) }
        }
    }
}