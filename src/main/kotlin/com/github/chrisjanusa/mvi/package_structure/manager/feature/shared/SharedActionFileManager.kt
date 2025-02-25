package com.github.chrisjanusa.mvi.package_structure.manager.feature.shared

import com.github.chrisjanusa.mvi.package_structure.manager.base.ActionFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.shared.helper.ISharedFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.shared.helper.SharedFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.shared.helper.SharedFileNameProvider
import com.github.chrisjanusa.mvi.package_structure.parent_provider.FeatureChild
import com.intellij.openapi.vfs.VirtualFile

class SharedActionFileManager(
    file: VirtualFile,
    sharedFileManager: ISharedFileManager = SharedFileManager(file)
) : ActionFileManager(file), ISharedFileManager by sharedFileManager, FeatureChild {

    companion object : SharedFileNameProvider(SUFFIX) {
        override fun createInstance(virtualFile: VirtualFile) = SharedActionFileManager(virtualFile)
        fun createNewInstance(insertionPackage: SharedPackage): SharedActionFileManager? {
            val fileName = getFileName(insertionPackage.featureName)
            return insertionPackage.createNewFile(
                fileName,
                SharedActionTemplate(insertionPackage, fileName)
                    .createContent()
            )?.let { SharedActionFileManager(it) }
        }
    }
}