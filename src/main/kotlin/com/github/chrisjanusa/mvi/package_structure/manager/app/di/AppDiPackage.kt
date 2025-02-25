package com.github.chrisjanusa.mvi.package_structure.manager.app.di

import com.github.chrisjanusa.mvi.helper.file_helper.findChildFile
import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.app.AppPackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootChild
import com.intellij.openapi.vfs.VirtualFile

class AppDiPackage(file: VirtualFile): PackageManager(file), RootChild {
    val appPackage by lazy {
        AppPackage(file.parent)
    }
    override val rootPackage by lazy {
        appPackage.rootPackage
    }

    val koinInit by lazy {
        file.findChildFile(KoinInitFileManager.NAME)?.let { KoinInitFileManager(it) }
    }
    val koinModule by lazy {
        file.findChildFile(KoinModuleFileManager.NAME)?.let { KoinModuleFileManager(it) }
    }

    private fun createAllChildren() {
        KoinInitFileManager.createNewInstance(this)
        KoinModuleFileManager.createNewInstance(this)
    }

    companion object : StaticChildInstanceCompanion("di", AppPackage) {
        override fun createInstance(virtualFile: VirtualFile) = AppDiPackage(virtualFile)
        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(KoinInitFileManager, KoinModuleFileManager)
        fun createNewInstance(insertionPackage: AppPackage): AppDiPackage? {
            val diPackage = insertionPackage.createNewDirectory(NAME)?.let { AppDiPackage(it) }
            diPackage?.createAllChildren()
            return diPackage
        }
    }
}