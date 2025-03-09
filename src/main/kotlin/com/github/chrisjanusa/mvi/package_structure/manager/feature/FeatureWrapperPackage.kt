package com.github.chrisjanusa.mvi.package_structure.manager.feature

import com.github.chrisjanusa.mvi.helper.file_helper.toPascalCase
import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.RootPackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootDirectChild
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.findFile

class FeatureWrapperPackage(file: VirtualFile) : PackageManager(file), RootDirectChild {
    val featureName = name.toPascalCase()
    override val rootPackage by lazy {
        RootPackage(file.parent)
    }

    val features by lazy {
        file.children.map { FeaturePackage(it) }
    }

    fun getFeatureWithName(featureName: String) {
        file.findFile(featureName)?.let { FeaturePackage(it) }
    }
    val allPlugins by lazy {
        features.flatMap { feature -> feature.plugins }
    }
    val allDomainModels by lazy {
        features.flatMap { feature -> feature.domainModels }
    }
    val allApis by lazy {
        features.flatMap { feature -> feature.apis }
    }

    fun createFeature(featureName: String) = FeaturePackage.createNewInstance(this, featureName)

    companion object : StaticChildInstanceCompanion(
        "feature",
        RootPackage
    ) {
        override fun createInstance(virtualFile: VirtualFile) = FeatureWrapperPackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(FeaturePackage)

        fun createNewInstance(insertionPackage: RootPackage): FeatureWrapperPackage? {
            val featureWrapperPackage = insertionPackage.createNewDirectory(NAME)?.let { FeatureWrapperPackage(it) }
            return featureWrapperPackage
        }
    }
}