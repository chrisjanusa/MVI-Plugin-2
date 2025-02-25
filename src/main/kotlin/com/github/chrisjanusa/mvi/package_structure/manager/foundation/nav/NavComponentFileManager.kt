package com.github.chrisjanusa.mvi.package_structure.manager.foundation.nav

import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootChild
import com.intellij.openapi.vfs.VirtualFile

open class NavComponentFileManager(file: VirtualFile) : FileManager(file), RootChild {
    val navPackage: FoundationNavPackage by lazy {
        FoundationNavPackage(file.parent)
    }
    val foundationPackage by lazy {
        navPackage.foundationPackage
    }
    override val rootPackage by lazy {
        foundationPackage.rootPackage
    }

    companion object : StaticChildInstanceCompanion("NavComponent", FoundationNavPackage) {
        override fun createInstance(virtualFile: VirtualFile) = NavComponentFileManager(virtualFile)
        fun createNewInstance(insertionPackage: FoundationNavPackage): NavComponentFileManager? {
            return insertionPackage.createNewFile(
                NAME,
                NavComponentTemplate(insertionPackage, NAME)
                    .createContent()
            )?.let { NavComponentFileManager(it) }
        }
    }
}