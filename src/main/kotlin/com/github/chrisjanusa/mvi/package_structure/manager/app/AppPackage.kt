package com.github.chrisjanusa.mvi.package_structure.manager.app

import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.hasPattern
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.RootPackage
import com.github.chrisjanusa.mvi.package_structure.manager.app.di.AppDiPackage
import com.github.chrisjanusa.mvi.package_structure.manager.app.effect.AppEffectPackage
import com.github.chrisjanusa.mvi.package_structure.manager.app.nav.AppNavPackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootDirectChild
import com.intellij.openapi.vfs.VirtualFile

class AppPackage(file: VirtualFile) : PackageManager(file), RootDirectChild {
    val appName by lazy {
        (activity ?: application ?: viewModel)?.appName
    }
    override val rootPackage by lazy {
        RootPackage(file.parent)
    }

    val navPackage by lazy {
        file.findChild(AppNavPackage.NAME)?.let { AppNavPackage(it) }
    }
    val navManager by lazy {
        navPackage?.navManager
    }

    val effectPackage by lazy {
        file.findChild(AppEffectPackage.NAME)?.let { AppEffectPackage(it) }
    }
    val navEffect by lazy {
        effectPackage?.navEffect
    }

    val diPackage by lazy {
        file.findChild(AppDiPackage.NAME)?.let { AppDiPackage(it) }
    }
    val koinInit by lazy {
        diPackage?.koinInit
    }
    val koinModule by lazy {
        diPackage?.koinModule
    }

    val activity by lazy {
        file.children.first { it.hasPattern("", AppActivityFileManager.SUFFIX) }?.let { AppActivityFileManager(it) }
    }
    val application by lazy {
        file.children.first { it.hasPattern("", AppApplicationFileManager.SUFFIX) }
            ?.let { AppApplicationFileManager(it) }
    }
    val viewModel by lazy {
        file.children.first { it.hasPattern("", AppViewModelFileManager.SUFFIX) }?.let { AppViewModelFileManager(it) }
    }

    private fun createAllChildren(appName: String) {
        AppNavPackage.createNewInstance(this)
        AppEffectPackage.createNewInstance(this)
        AppViewModelFileManager.createNewInstance(this, appName)
        AppActivityFileManager.createNewInstance(this, appName)
        AppDiPackage.createNewInstance(this)
        AppApplicationFileManager.createNewInstance(this, appName)
    }

    companion object : StaticChildInstanceCompanion("app", RootPackage) {
        override fun createInstance(virtualFile: VirtualFile) = AppPackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(
                AppActivityFileManager,
                AppApplicationFileManager,
                AppViewModelFileManager,
                AppDiPackage,
                AppNavPackage,
                AppEffectPackage
            )

        fun createNewInstance(insertionPackage: RootPackage, appName: String): AppPackage? {
            val appPackage = insertionPackage.createNewDirectory(NAME)?.let { AppPackage(it) }
            appPackage?.createAllChildren(appName)
            return appPackage
        }
    }
}