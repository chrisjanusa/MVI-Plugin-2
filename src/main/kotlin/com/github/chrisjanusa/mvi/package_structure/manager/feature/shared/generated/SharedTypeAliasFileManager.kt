package com.github.chrisjanusa.mvi.package_structure.manager.feature.shared.generated


import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.shared.helper.ISharedFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.shared.helper.SharedFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.shared.helper.SharedFileNameProvider
import com.github.chrisjanusa.mvi.package_structure.parent_provider.FeatureChild
import com.intellij.openapi.vfs.VirtualFile

class SharedTypeAliasFileManager(
    file: VirtualFile, 
    sharedFileManager: ISharedFileManager = SharedFileManager(file)
) : ISharedFileManager by sharedFileManager, FeatureChild, FileManager(file) {
    
    companion object : SharedFileNameProvider("TypeAlias") {
        override fun createInstance(virtualFile: VirtualFile) = SharedTypeAliasFileManager(virtualFile)
        fun createNewInstance(insertionPackage: SharedGeneratedPackage): SharedTypeAliasFileManager? {
            val fileName = getFileName(insertionPackage.featureName)
            return insertionPackage.createNewFile(
                fileName,
                SharedTypeAliasTemplate(insertionPackage, fileName)
                    .createContent()
            )?.let { SharedTypeAliasFileManager(it) }
        }
    }
}