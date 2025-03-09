package com.github.chrisjanusa.mvi.package_structure.manager

import com.github.chrisjanusa.mvi.helper.file_helper.getProject
import com.github.chrisjanusa.mvi.helper.file_helper.getProjectPackage
import com.github.chrisjanusa.mvi.helper.file_helper.pathToPackage
import com.github.chrisjanusa.mvi.package_structure.Manager
import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.ParentInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.app.AppPackage
import com.github.chrisjanusa.mvi.package_structure.manager.common.CommonPackage
import com.github.chrisjanusa.mvi.package_structure.manager.feature.FeatureWrapperPackage
import com.github.chrisjanusa.mvi.package_structure.manager.foundation.FoundationPackage
import com.github.chrisjanusa.mvi.package_structure.manager.ui.RootUiPackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootChild
import com.intellij.openapi.vfs.VirtualFile

class RootPackage(file: VirtualFile) : PackageManager(file) {
    val appPackage by lazy {
        file.findChild(AppPackage.NAME)?.let { AppPackage(it) }
    }
    val koinModule by lazy {
        appPackage?.koinModule
    }
    val foundationPackage by lazy {
        file.findChild(FoundationPackage.NAME)?.let { FoundationPackage(it) }
    }
    val commonPackage by lazy {
        file.findChild(CommonPackage.NAME)?.let { CommonPackage(it) }
    }
    val uiPackage by lazy {
        file.findChild(RootUiPackage.NAME)?.let { RootUiPackage(it) }
    }
    val featureWrapperPackage by lazy {
        file.findChild(FeatureWrapperPackage.NAME)?.let { FeatureWrapperPackage(it) }
    }
    val features by lazy {
        featureWrapperPackage?.features
    }
    val isInitialized by lazy {
        foundationPackage != null
    }

    fun createFeature(featureName: String) = featureWrapperPackage?.createFeature(featureName)

    fun createAllChildren(appName: String) {
        FoundationPackage.createNewInstance(this)
        CommonPackage.createNewInstance(this)
        AppPackage.createNewInstance(this, appName)
        FeatureWrapperPackage.createNewInstance(this)
    }

    companion object : ParentInstanceCompanion(FoundationPackage) {
        override fun createInstance(virtualFile: VirtualFile) = RootPackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(FeatureWrapperPackage, CommonPackage, FoundationPackage, AppPackage, RootUiPackage)

        override fun isInstance(file: VirtualFile?): Boolean {
            file ?: return false
            return super.isInstance(file) || file.getProject()?.getProjectPackage()?.moduleGradle?.getRootPackage() == file.path.pathToPackage()
        }

        fun getFromManager(manager: Manager): RootPackage? {
            if (manager is RootPackage) return manager
            val rootChild = manager as? RootChild ?: return null
            return rootChild.rootPackage
        }
    }
}