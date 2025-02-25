package com.github.chrisjanusa.mvi.package_structure.manager.foundation

import com.github.chrisjanusa.mvi.helper.file_helper.findChildFile
import com.github.chrisjanusa.mvi.helper.file_helper.toPascalCase
import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.RootPackage
import com.github.chrisjanusa.mvi.package_structure.manager.foundation.nav.FoundationNavPackage
import com.github.chrisjanusa.mvi.package_structure.manager.foundation.viewmodel.FoundationViewModelPackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootDirectChild
import com.intellij.openapi.vfs.VirtualFile

class FoundationPackage(file: VirtualFile): PackageManager(file), RootDirectChild {
    val featureName = name.toPascalCase()
    override val rootPackage by lazy {
        RootPackage(file.parent)
    }

    val navPackage by lazy {
        file.findChild(FoundationNavPackage.NAME)?.let { FoundationNavPackage(it) }
    }
    val navComponent by lazy {
        navPackage?.navComponent
    }
    val navComponentId by lazy {
        navPackage?.navComponentId
    }

    val viewModelPackage by lazy {
        file.findChild(FoundationViewModelPackage.NAME)?.let { FoundationViewModelPackage(it) }
    }
    val baseViewModel by lazy {
        viewModelPackage?.baseViewModel
    }
    val parentViewModel by lazy {
        viewModelPackage?.parentViewModel
    }
    val pluginViewModel by lazy {
        viewModelPackage?.pluginViewModel
    }
    val sharedViewModel by lazy {
        viewModelPackage?.sharedViewModel
    }

    val action by lazy {
        file.findChildFile(FoundationActionFileManager.NAME)?.let { FoundationActionFileManager(it) }
    }
    val effect by lazy {
        file.findChildFile(FoundationEffectFileManager.NAME)?.let { FoundationEffectFileManager(it) }
    }
    val plugin by lazy {
        file.findChildFile(FoundationPluginFileManager.NAME)?.let { FoundationPluginFileManager(it) }
    }
    val state by lazy {
        file.findChildFile(FoundationStateFileManager.NAME)?.let { FoundationStateFileManager(it) }
    }
    val slice by lazy {
        file.findChildFile(FoundationSliceFileManager.NAME)?.let { FoundationSliceFileManager(it) }
    }
    val sliceUpdate by lazy {
        file.findChildFile(FoundationSliceUpdateFileManager.NAME)?.let { FoundationSliceUpdateFileManager(it) }
    }

    private fun createAllChildren() {
        FoundationStateFileManager.createNewInstance(this)
        FoundationSliceFileManager.createNewInstance(this)
        FoundationSliceUpdateFileManager.createNewInstance(this)
        FoundationActionFileManager.createNewInstance(this)
        FoundationEffectFileManager.createNewInstance(this)
        FoundationViewModelPackage.createNewInstance(this)
        FoundationPluginFileManager.createNewInstance(this)
        FoundationNavPackage.createNewInstance(this)
    }

    companion object : StaticInstanceCompanion("foundation") {
        override fun createInstance(virtualFile: VirtualFile) = FoundationPackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(
                FoundationActionFileManager,
                FoundationEffectFileManager,
                FoundationStateFileManager,
                FoundationPluginFileManager,
                FoundationSliceFileManager,
                FoundationSliceUpdateFileManager,
                FoundationViewModelPackage,
                FoundationNavPackage
            )

        fun createNewInstance(insertionPackage: RootPackage): FoundationPackage? {
            val packageManager = insertionPackage.createNewDirectory(NAME)?.let { FoundationPackage(it) }
            packageManager?.createAllChildren()
            return packageManager
        }
    }
}