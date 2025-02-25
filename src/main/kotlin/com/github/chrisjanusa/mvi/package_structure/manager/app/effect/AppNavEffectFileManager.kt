package com.github.chrisjanusa.mvi.package_structure.manager.app.effect

import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootChild
import com.intellij.openapi.vfs.VirtualFile

class AppNavEffectFileManager(
    file: VirtualFile,
) : FileManager(file), RootChild {
    val effectModule by lazy {
        AppEffectPackage(file.parent)
    }
    override val rootPackage by lazy {
        effectModule.rootPackage
    }

    companion object : StaticChildInstanceCompanion("NavEffect", AppEffectPackage) {
        override fun createInstance(virtualFile: VirtualFile) = AppNavEffectFileManager(virtualFile)
        fun createNewInstance(insertionPackage: AppEffectPackage): AppNavEffectFileManager? {
            return insertionPackage.createNewFile(
                NAME,
                AppNavEffectTemplate(insertionPackage, NAME)
                    .createContent()
            )?.let { AppNavEffectFileManager(it) }
        }
    }
}