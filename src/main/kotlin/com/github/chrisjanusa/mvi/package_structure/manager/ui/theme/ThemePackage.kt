package com.github.chrisjanusa.mvi.package_structure.manager.ui.theme

import com.github.chrisjanusa.mvi.helper.file_helper.toPascalCase
import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.RootPackage
import com.github.chrisjanusa.mvi.package_structure.manager.ui.RootUiPackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootChild
import com.intellij.openapi.vfs.VirtualFile

class ThemePackage(file: VirtualFile) : PackageManager(file), RootChild {
    val featureName = name.toPascalCase()

    val uiPackage: RootUiPackage by lazy {
        RootUiPackage(file.parent)
    }
    override val rootPackage by lazy {
        uiPackage.rootPackage
    }

    companion object : StaticChildInstanceCompanion("theme", RootUiPackage) {
        override fun createInstance(virtualFile: VirtualFile) = ThemePackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf()
        fun createNewInstance(insertionPackage: RootPackage): ThemePackage? {
            val themePackage = insertionPackage.createNewDirectory(NAME)?.let { ThemePackage(it) }
            return themePackage
        }
    }
}