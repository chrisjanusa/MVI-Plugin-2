package com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin

import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticExcludeChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.common.CommonPackage
import com.github.chrisjanusa.mvi.package_structure.manager.feature.FeaturePackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.FeatureDirectChild
import com.intellij.openapi.vfs.VirtualFile

class PluginWrapperPackage(file: VirtualFile) : PackageManager(file), FeatureDirectChild {
    override val featurePackage by lazy {
        FeaturePackage(file.parent)
    }
    override val rootPackage by lazy {
        featurePackage.rootPackage
    }

    val plugins by lazy {
        file.children.map {
            PluginPackage(it)
        }
    }

    fun createPlugin(pluginName: String, hasState: Boolean, hasSlice: Boolean, isNavDestination: Boolean) {
        PluginPackage.createNewInstance(this, pluginName, hasState, hasSlice, isNavDestination)
    }

    companion object : StaticExcludeChildInstanceCompanion("plugin", CommonPackage) {
        override fun createInstance(virtualFile: VirtualFile) = PluginWrapperPackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(PluginPackage)

        fun createNewInstance(insertionPackage: FeaturePackage): PluginWrapperPackage? {
            return insertionPackage.createNewDirectory(NAME)?.let { PluginWrapperPackage(it) }
        }
    }
}