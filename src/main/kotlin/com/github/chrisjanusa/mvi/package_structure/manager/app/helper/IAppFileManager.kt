package com.github.chrisjanusa.mvi.package_structure.manager.app.helper
import com.github.chrisjanusa.mvi.package_structure.manager.app.AppPackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootChild

interface IAppFileManager: RootChild {
    val appPackage: AppPackage
}