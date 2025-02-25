package com.github.chrisjanusa.mvi.package_structure.manager.project.app

import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.project.ProjectPackage
import com.intellij.openapi.vfs.VirtualFile

class ProjectAppPackage(file: VirtualFile): PackageManager(file) {
    override val projectPackage by lazy {
        ProjectPackage(file.parent)
    }

    val moduleGradle by lazy {
        file.findChild(ModuleGradleManager.NAME)?.let { ModuleGradleManager(it) }
    }

    companion object : StaticChildInstanceCompanion("app", ProjectPackage) {
        override fun createInstance(virtualFile: VirtualFile) = ProjectAppPackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(ModuleGradleManager)
    }
}