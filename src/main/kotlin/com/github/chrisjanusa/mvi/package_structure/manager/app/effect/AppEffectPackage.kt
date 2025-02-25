package com.github.chrisjanusa.mvi.package_structure.manager.app.effect

import com.github.chrisjanusa.mvi.helper.file_helper.findChildFile
import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.app.AppPackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootChild
import com.intellij.openapi.vfs.VirtualFile

class AppEffectPackage(file: VirtualFile): PackageManager(file), RootChild {
    val appPackage by lazy {
        AppPackage(file.parent)
    }
    override val rootPackage by lazy {
        appPackage.rootPackage
    }

    val navEffect by lazy {
        file.findChildFile(AppNavEffectFileManager.NAME)?.let { AppNavEffectFileManager(it) }
    }

    private fun createAllChildren() {
        AppNavEffectFileManager.createNewInstance(this)
    }

    companion object : StaticChildInstanceCompanion("effect", AppPackage) {
        override fun createInstance(virtualFile: VirtualFile) = AppEffectPackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(AppNavEffectFileManager)
        fun createNewInstance(insertionPackage: AppPackage): AppEffectPackage? {
            val effectPackage = insertionPackage.createNewDirectory(NAME)?.let { AppEffectPackage(it) }
            effectPackage?.createAllChildren()
            return effectPackage
        }
    }
}