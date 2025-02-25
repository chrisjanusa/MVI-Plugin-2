package com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.ui

import com.github.chrisjanusa.mvi.helper.file_helper.findChildFile
import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.PluginPackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.PluginChild
import com.intellij.openapi.vfs.VirtualFile

class PluginUiPackage(file: VirtualFile) : PackageManager(file), PluginChild {
    override val pluginPackage by lazy {
        PluginPackage(file.parent)
    }
    val pluginName by lazy {
        pluginPackage.pluginName
    }
    val pluginWrapperPackage by lazy {
        pluginPackage.pluginWrapperPackage
    }
    override val featurePackage by lazy {
        pluginPackage.featurePackage
    }
    override val rootPackage by lazy {
        pluginPackage.rootPackage
    }

    val content by lazy {
        val sliceUpdateFile = file.findChildFile(PluginContentFileManager.getFileName(pluginName)) ?: return@lazy null
        PluginContentFileManager(sliceUpdateFile)
    }

    private fun createAllChildren(hasState: Boolean, hasSlice: Boolean) {
        PluginContentFileManager.createNewInstance(this, hasState, hasSlice)
    }

    companion object : StaticChildInstanceCompanion("ui", PluginPackage) {
        override fun createInstance(virtualFile: VirtualFile) = PluginUiPackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(PluginContentFileManager)

        fun createNewInstance(insertionPackage: PluginPackage, hasState: Boolean, hasSlice: Boolean): PluginUiPackage? {
            val packageManager = insertionPackage.createNewDirectory(NAME)?.let { PluginUiPackage(it) }
            packageManager?.createAllChildren(hasState, hasSlice)
            return packageManager
        }
    }
}