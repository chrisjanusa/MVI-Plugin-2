package com.github.chrisjanusa.mvi.package_structure.manager.feature.shared.generated

import com.github.chrisjanusa.mvi.helper.file_helper.findChildFile
import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.shared.SharedPackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.FeatureChild
import com.intellij.openapi.vfs.VirtualFile

class SharedGeneratedPackage(file: VirtualFile): PackageManager(file), FeatureChild {
    val sharedPackage by lazy {
        SharedPackage(file.parent)
    }
    val featureName by lazy {
        sharedPackage.featureName
    }
    override val featurePackage by lazy {
        sharedPackage.featurePackage
    }
    override val rootPackage by lazy {
        sharedPackage.rootPackage
    }

    val viewModel by lazy {
        val viewModelFile = file.findChildFile(FeatureSharedViewModelFileManager.getFileName(featureName)) ?: return@lazy null
        FeatureSharedViewModelFileManager(viewModelFile)
    }

    val typeAlias by lazy {
        val typeAliasFile = file.findChildFile(SharedTypeAliasFileManager.getFileName(featureName)) ?: return@lazy null
        SharedTypeAliasFileManager(typeAliasFile)
    }

    private fun createAllChildren() {
        SharedTypeAliasFileManager.createNewInstance(this)
        FeatureSharedViewModelFileManager.createNewInstance(this)
    }

    companion object : StaticInstanceCompanion("generated") {
        override fun createInstance(virtualFile: VirtualFile) = SharedGeneratedPackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(SharedTypeAliasFileManager, FeatureSharedViewModelFileManager)

        fun createNewInstance(insertionPackage: SharedPackage): SharedGeneratedPackage? {
            val packageManager = insertionPackage.createNewDirectory(NAME)?.let { SharedGeneratedPackage(it) }
            packageManager?.createAllChildren()
            return packageManager
        }
    }
}