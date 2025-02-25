package com.github.chrisjanusa.mvi.package_structure.manager.feature.nav

import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.FeaturePackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.FeatureDirectChild
import com.intellij.openapi.vfs.VirtualFile

class FeatureNavPackage(file: VirtualFile): PackageManager(file), FeatureDirectChild {
    override val featurePackage by lazy {
        FeaturePackage(file.parent)
    }
    val featureName by lazy {
        featurePackage.featureName
    }
    override val rootPackage by lazy {
        featurePackage.rootPackage
    }

    val navGraph by lazy {
        file.findChild(NavGraphFileManager.getFileName(featureName))?.let { NavGraphFileManager(it) }
    }

    private fun createAllChildren() {
        NavGraphFileManager.createNewInstance(this)
    }

    companion object : StaticInstanceCompanion("nav") {
        override fun createInstance(virtualFile: VirtualFile) = FeatureNavPackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(NavGraphFileManager)

        fun createNewInstance(insertionPackage: FeaturePackage): FeatureNavPackage? {
            val packageManager = insertionPackage.createNewDirectory(NAME)?.let { FeatureNavPackage(it) }
            packageManager?.createAllChildren()
            return packageManager
        }
    }
}