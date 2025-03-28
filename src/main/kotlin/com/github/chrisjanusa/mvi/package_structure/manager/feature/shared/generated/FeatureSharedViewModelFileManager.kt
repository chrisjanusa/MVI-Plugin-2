package com.github.chrisjanusa.mvi.package_structure.manager.feature.shared.generated

import com.github.chrisjanusa.mvi.package_structure.manager.ManagerProvider
import com.github.chrisjanusa.mvi.package_structure.manager.base.EffectFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.base.ViewModelFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.helper.PluginFileNameProvider
import com.github.chrisjanusa.mvi.package_structure.manager.feature.shared.helper.ISharedFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.shared.helper.SharedFileManager
import com.github.chrisjanusa.mvi.package_structure.parent_provider.FeatureChild
import com.intellij.openapi.vfs.VirtualFile

class FeatureSharedViewModelFileManager(
    file: VirtualFile,
    sharedFileManager: ISharedFileManager = SharedFileManager(file)
) : ViewModelFileManager(file), ISharedFileManager by sharedFileManager, FeatureChild {
    fun addSliceUpdateEffect(effectName: String, import: String?) {
        addAfterFirst("        ${effectName}${EffectFileManager.SUFFIX},") { line ->
            line.contains("val sliceUpdateEffectList")
        }
        if (import != null) {
            addImport(import)
        }
        writeToDisk()
    }

    companion object : PluginFileNameProvider(SUFFIX) {
        override fun createInstance(virtualFile: VirtualFile) = FeatureSharedViewModelFileManager(virtualFile)
        fun createNewInstance(insertionPackage: SharedGeneratedPackage): FeatureSharedViewModelFileManager? {
            val fileName = getFileName(insertionPackage.featureName)
            if (insertionPackage.viewModel == null) {
                val sharedViewModel = insertionPackage.createNewFile(
                    fileName,
                    FeatureSharedViewModelTemplate(insertionPackage, fileName)
                        .createContent()
                )?.let { FeatureSharedViewModelFileManager(it) }
                if (sharedViewModel != null) {
                    insertionPackage.featurePackage.navPackage?.navGraph?.let { navGraph ->
                        ManagerProvider.getAppActivity(sharedViewModel)?.addSharedViewModelToGraph(
                            navGraph = navGraph,
                            sharedViewModel = sharedViewModel
                        )
                    }
                    insertionPackage.rootPackage.koinModule?.addSharedViewModel(sharedViewModel)
                    insertionPackage.rootPackage.koinModule?.writeToDisk()
                }
                return sharedViewModel
            } else {
                return insertionPackage.viewModel
            }
        }
    }
}