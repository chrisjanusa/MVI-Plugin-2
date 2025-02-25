package com.github.chrisjanusa.mvi.package_structure.manager.feature.service.remote

import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.service.ServicePackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.FeatureChild
import com.intellij.openapi.vfs.VirtualFile

class FeatureRemotePackage(file: VirtualFile): PackageManager(file), FeatureChild {
    val servicePackage by lazy {
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

    companion object : StaticInstanceCompanion("remote") {
        override fun createInstance(virtualFile: VirtualFile) = FeatureRemotePackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf()

        fun createNewInstance(insertionPackage: ServicePackage): FeatureRemotePackage? {
            return insertionPackage.createNewDirectory(NAME)?.let { FeatureRemotePackage(it) }
        }
    }
}