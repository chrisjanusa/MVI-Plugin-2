package com.github.chrisjanusa.mvi.package_structure.manager.feature.service.mapper

import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.base.ModelFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.service.FeatureServicePackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.ServiceChild
import com.intellij.openapi.vfs.VirtualFile

class FeatureMapperPackage(file: VirtualFile): PackageManager(file), ServiceChild {
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
    val mappers by lazy {
        file.children.map { MapperFileManager(it) }
    }

    fun addMapper(from: ModelFileManager, to: ModelFileManager) {
        val mapperFileManager = MapperFileManager.createNewInstance(this, from.modelName)
        mapperFileManager?.addMapper(from, to)
        mapperFileManager?.writeToDisk()
    }

    companion object : StaticInstanceCompanion("mapper") {
        override fun createInstance(virtualFile: VirtualFile) = FeatureMapperPackage(virtualFile)


        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(MapperFileManager)

        fun createNewInstance(insertionPackage: FeatureServicePackage): FeatureMapperPackage? {
            return insertionPackage.createNewDirectory(NAME)?.let { FeatureMapperPackage(it) }
        }
    }
}