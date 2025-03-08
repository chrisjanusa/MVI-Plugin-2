package com.github.chrisjanusa.mvi.package_structure.manager.app.di

import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootChild
import com.intellij.openapi.vfs.VirtualFile

class KoinInitFileManager(file: VirtualFile) : FileManager(file), RootChild {
    val diModule by lazy {
        AppDiPackage(file.parent)
    }
    override val rootPackage by lazy {
        diModule.rootPackage
    }

    fun addModule(moduleName: String) {
        if (!documentText.contains(moduleName)) {
            addAfterFirst(
                lineToAdd = "            $moduleName,"
            ) { line ->
                line.contains("modules(")
            }
        }
    }

    companion object : StaticChildInstanceCompanion("KoinInit", AppDiPackage) {
        override fun createInstance(virtualFile: VirtualFile) = KoinInitFileManager(virtualFile)
        fun createNewInstance(insertionPackage: AppDiPackage): KoinInitFileManager? {
            return insertionPackage.createNewFile(
                NAME,
                KoinInitTemplate(insertionPackage, NAME)
                    .createContent()
            )?.let { KoinInitFileManager(it) }
        }
    }
}