package com.github.chrisjanusa.mvi.package_structure.manager.app.nav

import com.github.chrisjanusa.mvi.helper.file_helper.findChildFile
import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.app.AppPackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootChild
import com.intellij.openapi.vfs.VirtualFile

class AppNavPackage(file: VirtualFile): PackageManager(file), RootChild {
    val appPackage by lazy {
        AppPackage(file.parent)
    }
    override val rootPackage by lazy {
        appPackage.rootPackage
    }

    val navManager by lazy {
        file.findChildFile(AppNavManagerFileManager.NAME)?.let { AppNavManagerFileManager(it) }
    }

    private fun createAllChildren() {
        AppNavManagerFileManager.createNewInstance(this)
    }

    companion object : StaticChildInstanceCompanion("nav", AppPackage) {
        override fun createInstance(virtualFile: VirtualFile) = AppNavPackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(AppNavManagerFileManager)
        fun createNewInstance(insertionPackage: AppPackage): AppNavPackage? {
            val navPackage = insertionPackage.createNewDirectory(NAME)?.let { AppNavPackage(it) }
            navPackage?.createAllChildren()
            return navPackage
        }
    }
}