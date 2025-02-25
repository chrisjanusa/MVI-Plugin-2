package com.github.chrisjanusa.mvi.package_structure.manager.ui

import com.github.chrisjanusa.mvi.helper.file_helper.toPascalCase
import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.RootPackage
import com.github.chrisjanusa.mvi.package_structure.manager.ui.theme.ThemePackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootDirectChild
import com.intellij.openapi.vfs.VirtualFile

class RootUiPackage(file: VirtualFile) : PackageManager(file), RootDirectChild {
    val featureName = name.toPascalCase()
    override val rootPackage by lazy {
        RootPackage(file.parent)
    }

    val themePackage by lazy {
        file.findChild(ThemePackage.NAME)?.let { ThemePackage(it) }
    }

    companion object : StaticChildInstanceCompanion("ui", RootPackage) {
        override fun createInstance(virtualFile: VirtualFile) = RootUiPackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(ThemePackage)
        fun createNewInstance(insertionPackage: RootPackage): RootUiPackage? {
            val uiPackage = insertionPackage.createNewDirectory(NAME)?.let { RootUiPackage(it) }
            return uiPackage
        }
    }
}