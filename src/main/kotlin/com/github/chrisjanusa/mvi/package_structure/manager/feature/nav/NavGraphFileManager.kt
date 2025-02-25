package com.github.chrisjanusa.mvi.package_structure.manager.feature.nav

import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticSuffixChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.parent_provider.FeatureChild
import com.intellij.openapi.vfs.VirtualFile

class NavGraphFileManager(actionFile: VirtualFile) : FileManager(actionFile), FeatureChild {
    val navPackage: FeatureNavPackage by lazy {
        FeatureNavPackage(file.parent)
    }
    override val featurePackage by lazy {
        navPackage.featurePackage
    }
    val featureName by lazy {
        navPackage.featureName
    }
    override val rootPackage by lazy {
        navPackage.rootPackage
    }
    
    companion object : StaticSuffixChildInstanceCompanion("NavGraph", FeatureNavPackage) {
        override fun createInstance(virtualFile: VirtualFile) = NavGraphFileManager(virtualFile)
        fun getFileName(featureName: String) = "$featureName$SUFFIX"
        fun createNewInstance(insertionPackage: FeatureNavPackage): NavGraphFileManager? {
            val fileName = getFileName(insertionPackage.featureName)
            return insertionPackage.createNewFile(
                fileName,
                NavGraphTemplate(insertionPackage, fileName)
                    .createContent()
            )?.let { NavGraphFileManager(it) }
        }
    }
}