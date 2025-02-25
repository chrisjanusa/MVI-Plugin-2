package com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.generated

import com.github.chrisjanusa.mvi.helper.file_helper.findChildFile
import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.PluginPackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.PluginChild
import com.intellij.openapi.vfs.VirtualFile

class PluginGeneratedPackage(file: VirtualFile): PackageManager(file), PluginChild  {
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

    val sliceUpdate by lazy {
        val sliceUpdateFile = file.findChildFile(PluginSliceUpdateFileManager.getFileName(pluginName)) ?: return@lazy null
        PluginSliceUpdateFileManager(sliceUpdateFile)
    }

    val navDestination by lazy {
        val navDestinationFile = file.findChildFile(PluginNavDestinationFileManager.getFileName(pluginName)) ?: return@lazy null
        PluginNavDestinationFileManager(navDestinationFile)
    }

    val pluginClass by lazy {
        val pluginClassFile = file.findChildFile(PluginClassFileManager.getFileName(pluginName)) ?: return@lazy null
        PluginClassFileManager(pluginClassFile)
    }

    val viewModel by lazy {
        val viewModelFile = file.findChildFile(PluginViewModelFileManager.getFileName(pluginName)) ?: return@lazy null
        PluginViewModelFileManager(viewModelFile)
    }

    val typeAlias by lazy {
        val typeAliasFile = file.findChildFile(PluginTypeAliasFileManager.getFileName(pluginName)) ?: return@lazy null
        PluginTypeAliasFileManager(typeAliasFile)
    }

    internal fun addSlice() {
        PluginSliceUpdateFileManager.createNewInstance(this)
        typeAlias?.addSlice()
        viewModel?.addSlice()
        pluginClass?.addSlice()
    }

    internal fun removeSlice() {
        deleteFile("${pluginName}SliceUpdate.kt")
        typeAlias?.removeSlice()
        viewModel?.removeSlice()
        pluginClass?.removeSlice()
    }

    private fun createAllChildren(hasState: Boolean, hasSlice: Boolean, isNavDestination: Boolean) {
        if (hasSlice) {
            PluginSliceUpdateFileManager.createNewInstance(this)
        }
        PluginTypeAliasFileManager.createNewInstance(this, hasState, hasSlice)
        PluginViewModelFileManager.createNewInstance(this, hasState, hasSlice)
        PluginClassFileManager.createNewInstance(this, hasState, hasSlice)
        if (isNavDestination) {
            PluginNavDestinationFileManager.createNewInstance(this)
        }
    }

    companion object : StaticChildInstanceCompanion("generated", PluginPackage) {
        override fun createInstance(virtualFile: VirtualFile) = PluginGeneratedPackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(PluginTypeAliasFileManager, PluginViewModelFileManager, PluginClassFileManager, PluginNavDestinationFileManager, PluginSliceUpdateFileManager)
        fun createNewInstance(
            insertionPackage: PluginPackage,
            hasState: Boolean,
            hasSlice: Boolean,
            isNavDestination: Boolean
        ): PluginGeneratedPackage? {
            val generatedPackage = insertionPackage.createNewDirectory(NAME)?.let { PluginGeneratedPackage(it) }
            generatedPackage?.createAllChildren(hasState, hasSlice, isNavDestination)
            return generatedPackage
        }
    }
}