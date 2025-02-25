package com.github.chrisjanusa.mvi.package_structure.manager.project.gradle

import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.project.ProjectPackage
import com.intellij.openapi.vfs.VirtualFile

class GradlePackage(file: VirtualFile): PackageManager(file) {
    override val projectPackage by lazy {
        ProjectPackage(file.parent)
    }

    val libs by lazy {
        file.findChild(LibsManager.NAME)?.let { LibsManager(it) }
    }

    companion object : StaticInstanceCompanion("gradle") {
        override fun createInstance(virtualFile: VirtualFile) = GradlePackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(LibsManager)
    }
}