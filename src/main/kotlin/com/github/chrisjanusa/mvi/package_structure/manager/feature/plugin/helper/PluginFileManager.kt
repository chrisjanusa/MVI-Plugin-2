package com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.helper

import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.PluginPackage
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.PluginSliceFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.PluginStateFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.generated.PluginGeneratedPackage
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.ui.PluginUiPackage
import com.github.chrisjanusa.mvi.package_structure.manager.foundation.FoundationSliceFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.foundation.FoundationStateFileManager
import com.intellij.openapi.vfs.VirtualFile

open class PluginFileManager(file: VirtualFile) : IPluginFileManager, FileManager(file) {
    private val isGenerated = PluginGeneratedPackage.isInstance(file.parent)
    private val isUi = PluginUiPackage.isInstance(file.parent)
    private val isPluginRoot = !isGenerated && !isUi
    override val uiPackage: PluginUiPackage? by lazy {
        if (PluginUiPackage.isInstance(file.parent)) {
            PluginUiPackage(file.parent)
        } else {
            pluginPackage.uiPackage
        }
    }
    override val generatedPackage: PluginGeneratedPackage? by lazy {
        if (isGenerated) PluginGeneratedPackage(file.parent) else pluginPackage.generatedPackage
    }
    override val pluginPackage: PluginPackage by lazy {
        when {
            isUi -> uiPackage?.pluginPackage ?: PluginPackage(file.parent)
            isGenerated -> generatedPackage?.pluginPackage ?: PluginPackage(file.parent)
            else -> PluginPackage(file.parent)
        }
    }
    override val pluginName by lazy {
        pluginPackage.pluginName
    }
    override val featurePackage by lazy {
        pluginPackage.featurePackage
    }
    override val rootPackage by lazy {
        featurePackage.rootPackage
    }

    private val sliceName = PluginSliceFileManager.getFileName(pluginPackage.pluginName)
    private val noSliceName = FoundationSliceFileManager.NO_SLICE
    private val slicePackage = "${pluginPackage.packagePath}.$sliceName"
    private val noSlicePackage = "${rootPackage.foundationPackage?.slice?.noSlicePackagePath}"
    private val stateName = PluginStateFileManager.getFileName(pluginPackage.pluginName)
    private val noStateName = FoundationStateFileManager.NO_STATE
    private val statePackage = "${pluginPackage.packagePath}.$stateName"
    private val noStatePackage = "${rootPackage.foundationPackage?.state?.noStatePackagePath}"

    override fun addSlice() {
        if (isPluginRoot) {
            removeImport(noSlicePackage)
        } else {
            findAndReplace(noSlicePackage, slicePackage)
        }
        findAndReplace(noSliceName, sliceName)
        if (isGenerated) {
            addImport(slicePackage)
        }
        writeToDisk()
    }

    override fun removeSlice() {
        if (isPluginRoot) {
            addImport(noSlicePackage)
        }
        findAndReplace(slicePackage, noSlicePackage)
        findAndReplace(sliceName, noSliceName)
        if (isGenerated) {
            removeImport(slicePackage)
        }
        writeToDisk()
    }

    override fun addState() {
        if (isPluginRoot) {
            removeImport(noStatePackage)
        } else {
            findAndReplace(noStatePackage, statePackage)
        }
        findAndReplace(noStateName, sliceName)
        if (isGenerated) {
            addImport(statePackage)
        }
        writeToDisk()
    }

    override fun removeState() {
        if (isPluginRoot) {
            addImport(noStatePackage)
        }
        findAndReplace(statePackage, noStatePackage)
        findAndReplace(sliceName, noStateName)
        if (isGenerated) {
            removeImport(statePackage)
        }
        writeToDisk()
    }
}