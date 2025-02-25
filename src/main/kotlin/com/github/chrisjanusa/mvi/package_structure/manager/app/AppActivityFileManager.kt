package com.github.chrisjanusa.mvi.package_structure.manager.app

import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.manager.app.helper.AppFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.app.helper.AppFileNameProvider
import com.github.chrisjanusa.mvi.package_structure.manager.app.helper.AppNameProvider
import com.github.chrisjanusa.mvi.package_structure.manager.app.helper.IAppFileManager
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootChild
import com.intellij.openapi.vfs.VirtualFile

class AppActivityFileManager(
    file: VirtualFile,
    appFileManager: AppFileManager = AppFileManager(file)
) : IAppFileManager by appFileManager, FileManager(file), RootChild, AppNameProvider {
    override val appName: String by lazy {
        name.substringBefore(SUFFIX)
    }

    companion object : AppFileNameProvider("Activity") {
        override fun createInstance(virtualFile: VirtualFile) = AppActivityFileManager(virtualFile)
        fun createNewInstance(insertionPackage: AppPackage, appName: String): AppActivityFileManager? {
            val fileName = getFileName(appName)
            return insertionPackage.createNewFile(
                fileName,
                AppActivityTemplate(insertionPackage, fileName)
                    .createContent()
            )?.let { AppActivityFileManager(it) }
        }
    }
}