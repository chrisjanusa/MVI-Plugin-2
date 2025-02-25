package com.github.chrisjanusa.mvi.package_structure.manager.feature.service.mapper

import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.service.ServicePackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.FeatureChild
import com.intellij.openapi.vfs.VirtualFile

class FeatureMapperPackage(file: VirtualFile): PackageManager(file), FeatureChild {
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

    companion object : StaticInstanceCompanion("mapper") {
        override fun createInstance(virtualFile: VirtualFile) = FeatureMapperPackage(virtualFile)


        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(ServicePackage)

        fun createNewInstance(insertionPackage: ServicePackage): FeatureMapperPackage? {
            return insertionPackage.createNewDirectory(NAME)?.let { FeatureMapperPackage(it) }
        }
    }
}