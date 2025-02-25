package com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin

import com.github.chrisjanusa.mvi.helper.file_helper.findChildFile
import com.github.chrisjanusa.mvi.helper.file_helper.toPascalCase
import com.github.chrisjanusa.mvi.package_structure.Manager
import com.github.chrisjanusa.mvi.package_structure.instance_companion.ChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.generated.PluginGeneratedPackage
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.ui.PluginUiPackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.FeatureChild
import com.github.chrisjanusa.mvi.package_structure.parent_provider.PluginChild
import com.intellij.openapi.vfs.VirtualFile

class PluginPackage(file: VirtualFile) : PackageManager(file), FeatureChild {
    val pluginName = name.toPascalCase()
    val generatedPackage by lazy {
        file.findChild(PluginGeneratedPackage.NAME)?.let { PluginGeneratedPackage(it) }
    }
    val uiPackage by lazy {
        file.findChild(PluginUiPackage.NAME)?.let { PluginUiPackage(it) }
    }
    val pluginWrapperPackage by lazy {
        PluginWrapperPackage(file.parent)
    }
    override val featurePackage by lazy {
        pluginWrapperPackage.featurePackage
    }
    override val rootPackage by lazy {
        pluginWrapperPackage.rootPackage
    }

    val pluginAction by lazy {
        val actionFile = file.findChildFile(PluginActionFileManager.getFileName(pluginName)) ?: return@lazy null
        PluginActionFileManager(actionFile)
    }

    val pluginEffect by lazy {
        val effectFile = file.findChildFile(PluginEffectFileManager.getFileName(pluginName)) ?: return@lazy null
        PluginEffectFileManager(effectFile)
    }

    val state by lazy {
        val stateFile = file.findChildFile(PluginStateFileManager.getFileName(pluginName)) ?: return@lazy null
        PluginStateFileManager(stateFile)
    }

    val slice by lazy {
        val sliceFile = file.findChildFile(PluginSliceFileManager.getFileName(pluginName)) ?: return@lazy null
        PluginSliceFileManager(sliceFile)
    }

    val sliceUpdate by lazy {
        generatedPackage?.sliceUpdate
    }

    val navDestination by lazy {
        generatedPackage?.navDestination
    }

    val pluginClass by lazy {
        generatedPackage?.pluginClass
    }

    val viewModel by lazy {
        generatedPackage?.viewModel
    }

    val typeAlias by lazy {
        generatedPackage?.typeAlias
    }

    val content by lazy {
        uiPackage?.content
    }

    val hasSlice by lazy {
        slice != null
    }

    fun addSlice() {
        PluginSliceFileManager.createNewInstance(this)
        generatedPackage?.addSlice()
        pluginAction?.addSlice()
        content?.addSlice()
    }

    fun removeSlice() {
        deleteFile("${pluginName}Slice.kt")
        generatedPackage?.removeSlice()
        pluginAction?.removeSlice()
        content?.removeSlice()
    }

    private fun createAllChildren(hasState: Boolean, hasSlice: Boolean, isNavDestination: Boolean) {
        if (hasSlice) {
            PluginSliceFileManager.createNewInstance(this)
        }
        if (hasState) {
            PluginStateFileManager.createNewInstance(this)
        }
        PluginActionFileManager.createNewInstance(this, hasState, hasSlice)
        PluginEffectFileManager.createNewInstance(this)
        PluginUiPackage.createNewInstance(this, hasState, hasSlice)
        PluginGeneratedPackage.createNewInstance(this, hasState, hasSlice, isNavDestination)
    }

    companion object : ChildInstanceCompanion(PluginWrapperPackage) {
        override fun createInstance(virtualFile: VirtualFile) = PluginPackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(
                PluginSliceFileManager,
                PluginActionFileManager,
                PluginEffectFileManager,
                PluginStateFileManager,
                PluginUiPackage,
                PluginGeneratedPackage
            )

        fun getFromManager(manager: Manager): PluginPackage? {
            if (manager is PluginPackage) return manager
            val pluginChild = manager as? PluginChild ?: return null
            return pluginChild.pluginPackage
        }

        fun createNewInstance(
            insertionPackage: PluginWrapperPackage,
            pluginName: String,
            hasState: Boolean,
            hasSlice: Boolean,
            isNavDestination: Boolean
        ): PluginPackage? {
            val packageManager = insertionPackage.createNewDirectory(pluginName)?.let { PluginPackage(it) }
            packageManager?.createAllChildren(hasState, hasSlice, isNavDestination)
            return packageManager
        }
    }
}