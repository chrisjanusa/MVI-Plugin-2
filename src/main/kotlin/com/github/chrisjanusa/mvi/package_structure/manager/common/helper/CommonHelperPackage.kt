package com.github.chrisjanusa.mvi.package_structure.manager.common.helper

import com.github.chrisjanusa.mvi.helper.file_helper.findChildFile
import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.common.CommonPackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootChild
import com.intellij.openapi.vfs.VirtualFile

class CommonHelperPackage(file: VirtualFile): PackageManager(file), RootChild {
    val commonPackage by lazy {
        CommonPackage(file.parent)
    }
    override val rootPackage by lazy {
        commonPackage.rootPackage
    }

    val classNameHelper by lazy {
        file.findChildFile(ClassNameHelperFileManager.NAME)?.let { ClassNameHelperFileManager(it) }
    }

    private fun createAllChildren() {
        ClassNameHelperFileManager.createNewInstance(this)
    }

    companion object : StaticChildInstanceCompanion("helper", CommonPackage) {
        override fun createInstance(virtualFile: VirtualFile) = CommonHelperPackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(ClassNameHelperFileManager)
        fun createNewInstance(insertionPackage: CommonPackage): CommonHelperPackage? {
            val helperPackage = insertionPackage.createNewDirectory(NAME)?.let { CommonHelperPackage(it) }
            helperPackage?.createAllChildren()
            return helperPackage
        }
    }
}