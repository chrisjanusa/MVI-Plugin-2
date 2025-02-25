package com.github.chrisjanusa.mvi.package_structure.manager.app.nav

import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootChild
import com.intellij.openapi.vfs.VirtualFile

class AppNavManagerFileManager(
    file: VirtualFile,
): FileManager(file), RootChild {
    val navModule by lazy {
        AppNavPackage(file.parent)
    }
    override val rootPackage by lazy {
        navModule.rootPackage
    }

    companion object : StaticChildInstanceCompanion("NavManager", AppNavPackage) {
        override fun createInstance(virtualFile: VirtualFile) = AppNavManagerFileManager(virtualFile)
        fun createNewInstance(insertionPackage: AppNavPackage): AppNavManagerFileManager? {
            return insertionPackage.createNewFile(
                NAME,
                AppNavManagerTemplate(insertionPackage, NAME)
                    .createContent()
            )?.let { AppNavManagerFileManager(it) }
        }
    }
}