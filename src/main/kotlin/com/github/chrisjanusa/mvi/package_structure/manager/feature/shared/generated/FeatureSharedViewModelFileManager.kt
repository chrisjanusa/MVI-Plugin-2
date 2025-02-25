package com.github.chrisjanusa.mvi.package_structure.manager.feature.shared.generated

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

    companion object : PluginFileNameProvider(SUFFIX) {
        override fun createInstance(virtualFile: VirtualFile) = FeatureSharedViewModelFileManager(virtualFile)
        fun createNewInstance(insertionPackage: SharedGeneratedPackage): FeatureSharedViewModelFileManager? {
            val fileName = getFileName(insertionPackage.featureName)
            if (insertionPackage.viewModel == null) {
                insertionPackage.rootPackage.koinModule?.addSharedViewModel(insertionPackage.featureName)
                return insertionPackage.createNewFile(
                    fileName,
                    FeatureSharedViewModelTemplate(insertionPackage, fileName)
                        .createContent()
                )?.let { FeatureSharedViewModelFileManager(it) }
            } else {
                return insertionPackage.viewModel
            }
        }
    }
}