package com.github.chrisjanusa.mvi.package_structure.manager.feature

import com.github.chrisjanusa.mvi.helper.file_helper.toPascalCase
import com.github.chrisjanusa.mvi.package_structure.Manager
import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.ParentInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.RootPackage
import com.github.chrisjanusa.mvi.package_structure.manager.feature.api.ApiPackage
import com.github.chrisjanusa.mvi.package_structure.manager.feature.domain_model.DomainModelPackage
import com.github.chrisjanusa.mvi.package_structure.manager.feature.nav.FeatureNavPackage
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.PluginWrapperPackage
import com.github.chrisjanusa.mvi.package_structure.manager.feature.service.FeatureServicePackage
import com.github.chrisjanusa.mvi.package_structure.manager.feature.shared.SharedPackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.FeatureChild
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootDirectChild
import com.intellij.openapi.vfs.VirtualFile

class FeaturePackage(file: VirtualFile) : PackageManager(file), RootDirectChild {
    val featureName = name.toPascalCase()
    override val rootPackage by lazy {
        RootPackage(file.parent)
    }

    val pluginWrapperPackage by lazy {
        file.findChild(PluginWrapperPackage.NAME)?.let { PluginWrapperPackage(it) }
    }
    val plugins by lazy {
        pluginWrapperPackage?.plugins ?: emptyList()
    }

    val domainModelPackage by lazy {
        file.findChild(DomainModelPackage.NAME)?.let { DomainModelPackage(it) }
    }
    val domainModels by lazy {
        domainModelPackage?.domainModels ?: emptyList()
    }

    val navPackage by lazy {
        file.findChild(FeatureNavPackage.NAME)?.let { FeatureNavPackage(it) }
    }

    val apiPackage by lazy {
        file.findChild(ApiPackage.NAME)?.let { ApiPackage(it) }
    }
    val apis by lazy {
        apiPackage?.apis ?: emptyList()
    }

    fun findAPI(repositoryName: String) = apiPackage?.findAPI(repositoryName)

    val servicePackage by lazy {
        file.findChild(FeatureServicePackage.NAME)?.let { FeatureServicePackage(it) }
    }
    val repositories by lazy {
        servicePackage?.repositories ?: emptyList()
    }

    val sharedPackage by lazy {
        file.findChild(SharedPackage.NAME)?.let { SharedPackage(it) }
    }

    fun createNavGraph() = FeatureNavPackage.createNewInstance(this)
    fun createServicePackage() = FeatureServicePackage.createNewInstance(this)
    fun createDomainModelPackage() = DomainModelPackage.createNewInstance(this)
    fun createSharedState() = SharedPackage.createNewInstance(this)
    fun createApiPackage() = ApiPackage.createNewInstance(this)

    private fun createAllChildren() {
        PluginWrapperPackage.createNewInstance(this)
    }

    companion object : ParentInstanceCompanion(
        FeatureServicePackage,
        PluginWrapperPackage,
        DomainModelPackage
    ) {
        override fun createInstance(virtualFile: VirtualFile) = FeaturePackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(FeatureNavPackage, FeatureServicePackage, DomainModelPackage, PluginWrapperPackage, SharedPackage, ApiPackage)

        fun getFromManager(manager: Manager): FeaturePackage? {
            if (manager is FeaturePackage) return manager
            val featureChild = manager as? FeatureChild ?: return null
            return featureChild.featurePackage
        }

        fun createNewInstance(insertionPackage: RootPackage, featureName: String): FeaturePackage? {
            val featurePackage = insertionPackage.createNewDirectory(featureName)?.let { FeaturePackage(it) }
            featurePackage?.createAllChildren()
            return featurePackage
        }
    }
}