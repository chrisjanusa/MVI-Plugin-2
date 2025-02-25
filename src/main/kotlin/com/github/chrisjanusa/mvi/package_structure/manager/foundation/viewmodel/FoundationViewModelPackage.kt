package com.github.chrisjanusa.mvi.package_structure.manager.foundation.viewmodel

import com.github.chrisjanusa.mvi.helper.file_helper.findChildFile
import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.foundation.FoundationPackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootChild
import com.intellij.openapi.vfs.VirtualFile

class FoundationViewModelPackage(file: VirtualFile): PackageManager(file), RootChild {
    val foundationPackage by lazy {
        FoundationPackage(file.parent)
    }
    override val rootPackage by lazy {
        foundationPackage.rootPackage
    }

    val baseViewModel by lazy {
        file.findChildFile(BaseViewModelFileManager.NAME)?.let { BaseViewModelFileManager(it) }
    }
    val pluginViewModel by lazy {
        file.findChildFile(FoundationPluginViewModelFileManager.NAME)?.let { FoundationPluginViewModelFileManager(it) }
    }
    val sharedViewModel by lazy {
        file.findChildFile(FoundationSharedViewModelFileManager.NAME)?.let { FoundationSharedViewModelFileManager(it) }
    }
    val parentViewModel by lazy {
        file.findChildFile(FoundationParentViewModelFileManager.NAME)?.let { FoundationParentViewModelFileManager(it) }
    }

    private fun createAllChildren() {
        BaseViewModelFileManager.createNewInstance(this)
        FoundationPluginViewModelFileManager.createNewInstance(this)
        FoundationSharedViewModelFileManager.createNewInstance(this)
        FoundationParentViewModelFileManager.createNewInstance(this)
    }

    companion object : StaticChildInstanceCompanion("view_model", FoundationPackage) {
        override fun createInstance(virtualFile: VirtualFile) = FoundationViewModelPackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(
                BaseViewModelFileManager,
                FoundationPluginViewModelFileManager,
                FoundationSharedViewModelFileManager,
                FoundationParentViewModelFileManager
            )

        fun createNewInstance(insertionPackage: FoundationPackage): FoundationViewModelPackage? {
            val packageManager = insertionPackage.createNewDirectory(NAME)?.let { FoundationViewModelPackage(it) }
            packageManager?.createAllChildren()
            return packageManager
        }
    }
}