package com.github.chrisjanusa.mvi.package_structure.manager.common.nav

import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.base.ActionFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.common.CommonPackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.CommonChild
import com.intellij.openapi.vfs.VirtualFile

open class CoreNavActionFileManager(file: VirtualFile) : ActionFileManager(file), CommonChild {
    override val commonPackage by lazy {
        CommonPackage(file.parent)
    }
    override val rootPackage by lazy {
        commonPackage.rootPackage
    }

    companion object : StaticChildInstanceCompanion("CoreNavAction", CommonNavPackage) {
        override fun createInstance(virtualFile: VirtualFile) = CoreNavActionFileManager(virtualFile)
        fun createNewInstance(insertionPackage: CommonNavPackage): CoreNavActionFileManager? {
            return insertionPackage.createNewFile(
                NAME,
                CoreNavActionTemplate(insertionPackage, NAME)
                    .createContent()
            )?.let { CoreNavActionFileManager(it) }
        }
    }
}