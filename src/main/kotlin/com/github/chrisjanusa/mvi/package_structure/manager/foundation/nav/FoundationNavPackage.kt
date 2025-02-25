package com.github.chrisjanusa.mvi.package_structure.manager.foundation.nav

import com.github.chrisjanusa.mvi.helper.file_helper.findChildFile
import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.foundation.FoundationPackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootChild
import com.intellij.openapi.vfs.VirtualFile

class FoundationNavPackage(file: VirtualFile): PackageManager(file), RootChild  {
    val foundationPackage by lazy {
        FoundationPackage(file.parent)
    }
    override val rootPackage by lazy {
        foundationPackage.rootPackage
    }

    val navComponent by lazy {
        file.findChildFile(NavComponentFileManager.NAME)?.let { NavComponentFileManager(it) }
    }
    val navComponentId by lazy {
        file.findChildFile(NavComponentIdFileManager.NAME)?.let { NavComponentIdFileManager(it) }
    }

    private fun createAllChildren() {
        NavComponentFileManager.createNewInstance(this)
        NavComponentIdFileManager.createNewInstance(this)
    }

    companion object : StaticChildInstanceCompanion("nav", FoundationPackage) {
        override fun createInstance(virtualFile: VirtualFile) = FoundationNavPackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(NavComponentFileManager, NavComponentIdFileManager)

        fun createNewInstance(insertionPackage: FoundationPackage): FoundationNavPackage? {
            val packageManager = insertionPackage.createNewDirectory(NAME)?.let { FoundationNavPackage(it) }
            packageManager?.createAllChildren()
            return packageManager
        }
    }
}