package com.github.chrisjanusa.mvi.package_structure.manager.app.helper

import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.manager.app.AppPackage
import com.intellij.openapi.vfs.VirtualFile

class AppFileManager(file: VirtualFile) : IAppFileManager, FileManager(file) {
    override val appPackage by lazy {
        AppPackage(file.parent)
    }
    override val rootPackage by lazy {
        appPackage.rootPackage
    }
}