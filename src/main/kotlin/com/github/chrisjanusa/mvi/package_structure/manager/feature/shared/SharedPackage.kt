package com.github.chrisjanusa.mvi.package_structure.manager.feature.shared

import com.github.chrisjanusa.mvi.helper.file_helper.findChildFile
import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.FeaturePackage
import com.github.chrisjanusa.mvi.package_structure.manager.feature.shared.generated.SharedGeneratedPackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.FeatureDirectChild
import com.intellij.openapi.vfs.VirtualFile

class SharedPackage(file: VirtualFile): PackageManager(file), FeatureDirectChild {
    val generatedPackage by lazy {
        file.findChild(SharedGeneratedPackage.NAME)?.let { SharedGeneratedPackage(it) }
    }
    override val featurePackage by lazy {
        FeaturePackage(file.parent)
    }
    val featureName by lazy {
        featurePackage.featureName
    }
    override val rootPackage by lazy {
        featurePackage.rootPackage
    }

    val action by lazy {
        val actionFile = file.findChildFile(SharedActionFileManager.getFileName(featureName)) ?: return@lazy  null
        SharedActionFileManager(actionFile)
    }

    val effect by lazy {
        val effectFile = file.findChildFile(SharedEffectFileManager.getFileName(featureName)) ?: return@lazy null
        SharedEffectFileManager(effectFile)
    }

    val state by lazy {
        val stateFile = file.findChildFile(SharedStateFileManager.getFileName(featureName)) ?: return@lazy null
        SharedStateFileManager(stateFile)
    }

    val viewModel by lazy {
        generatedPackage?.viewModel
    }

    val typeAlias by lazy {
        generatedPackage?.typeAlias
    }

    private fun createAllChildren() {
        SharedStateFileManager.createNewInstance(this)
        SharedEffectFileManager.createNewInstance(this)
        SharedActionFileManager.createNewInstance(this)
        SharedGeneratedPackage.createNewInstance(this)
    }

    companion object : StaticInstanceCompanion("shared") {
        override fun createInstance(virtualFile: VirtualFile) = SharedGeneratedPackage(virtualFile)
        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(SharedStateFileManager, SharedEffectFileManager, SharedActionFileManager, SharedGeneratedPackage)

        fun createNewInstance(insertionPackage: FeaturePackage): SharedPackage? {
            val packageManager = insertionPackage.createNewDirectory(NAME)?.let { SharedPackage(it) }
            packageManager?.createAllChildren()
            return packageManager
        }
    }
}