package com.github.chrisjanusa.mvi.package_structure.manager.common.nav

import com.github.chrisjanusa.mvi.helper.file_helper.findChildFile
import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.common.CommonPackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.CommonChild
import com.intellij.openapi.vfs.VirtualFile

class CommonNavPackage(file: VirtualFile) : PackageManager(file), CommonChild {
    override val commonPackage by lazy {
        CommonPackage(file.parent)
    }
    override val rootPackage by lazy {
        commonPackage.rootPackage
    }

    val coreNavAction by lazy {
        file.findChildFile(CoreNavActionFileManager.NAME)?.let { CoreNavActionFileManager(it) }
    }

    private fun createAllChildren() {
        CoreNavActionFileManager.createNewInstance(this)
    }

    companion object : StaticChildInstanceCompanion("nav", CommonPackage) {
        override fun createInstance(virtualFile: VirtualFile) = CommonNavPackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(CoreNavActionFileManager)
        fun createNewInstance(insertionPackage: CommonPackage): CommonNavPackage? {
            val navPackage = insertionPackage.createNewDirectory(NAME)?.let { CommonNavPackage(it) }
            navPackage?.createAllChildren()
            return navPackage
        }
    }
}