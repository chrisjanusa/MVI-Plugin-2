package com.github.chrisjanusa.mvi.package_structure.manager.app

import com.github.chrisjanusa.mvi.package_structure.manager.app.helper.AppFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.app.helper.AppFileNameProvider
import com.github.chrisjanusa.mvi.package_structure.manager.app.helper.AppNameProvider
import com.github.chrisjanusa.mvi.package_structure.manager.app.helper.IAppFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.base.ViewModelFileManager
import com.intellij.openapi.vfs.VirtualFile

class AppViewModelFileManager(
    file: VirtualFile,
    appFileManager: AppFileManager = AppFileManager(file)
) : ViewModelFileManager(file), IAppFileManager by appFileManager, AppNameProvider {
    override val appName: String by lazy {
        name.substringBefore(SUFFIX)
    }

    companion object : AppFileNameProvider(SUFFIX) {
        override fun createInstance(virtualFile: VirtualFile) = AppViewModelFileManager(virtualFile)
        fun createNewInstance(insertionPackage: AppPackage, appName: String): AppViewModelFileManager? {
            val fileName = getFileName(appName)
            return insertionPackage.createNewFile(
                fileName,
                AppViewModelTemplate(insertionPackage, fileName)
                    .createContent()
            )?.let { AppViewModelFileManager(it) }
        }
    }
}