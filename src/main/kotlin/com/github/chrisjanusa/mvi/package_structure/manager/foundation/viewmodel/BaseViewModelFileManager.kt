package com.github.chrisjanusa.mvi.package_structure.manager.foundation.viewmodel

import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootChild
import com.intellij.openapi.vfs.VirtualFile

open class BaseViewModelFileManager(file: VirtualFile) : FileManager(file), RootChild {
    val viewModelPackage: FoundationViewModelPackage by lazy {
        FoundationViewModelPackage(file.parent)
    }
    val foundationPackage by lazy {
        viewModelPackage.foundationPackage
    }
    override val rootPackage by lazy {
        foundationPackage.rootPackage
    }

    companion object : StaticChildInstanceCompanion("BaseViewModel", FoundationViewModelPackage) {
        override fun createInstance(virtualFile: VirtualFile) = BaseViewModelFileManager(virtualFile)
        fun createNewInstance(insertionPackage: FoundationViewModelPackage): BaseViewModelFileManager? {
            return insertionPackage.createNewFile(
                NAME,
                BaseViewModelTemplate(insertionPackage, NAME)
                    .createContent()
            )?.let { BaseViewModelFileManager(it) }
        }
    }
}