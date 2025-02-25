package com.github.chrisjanusa.mvi.package_structure.manager.project

import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.ParentInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.project.app.ProjectAppPackage
import com.github.chrisjanusa.mvi.package_structure.manager.project.gradle.GradlePackage
import com.github.chrisjanusa.mvi.package_structure.manager.project.library.LibraryManager
import com.github.chrisjanusa.mvi.package_structure.manager.project.manifest.ManifestManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.findFile

class ProjectPackage(file: VirtualFile): PackageManager(file) {
    val gradlePackage by lazy {
        file.findChild(GradlePackage.NAME)?.let { GradlePackage(it) }
    }
    val libs by lazy {
        gradlePackage?.libs
    }

    val projectGradle by lazy {
        file.findChild(ProjectGradleManager.NAME)?.let { ProjectGradleManager(it) }
    }

    val appPackage by lazy {
        file.findChild(ProjectAppPackage.NAME)?.let { ProjectAppPackage(it) }
    }

    val moduleGradle by lazy {
        appPackage?.moduleGradle
    }

    val manifest by lazy {
        file.findFile(ManifestManager.PATH_FROM_PROJECT)?.let { ManifestManager(it) }
    }

    val libraryManager by lazy {
        val libsManager = libs
        val projectGradleManager = projectGradle
        val moduleGradleManager = moduleGradle
        if (libsManager == null || projectGradleManager == null || moduleGradleManager == null) return@lazy null
        LibraryManager(
            libsManager = libsManager,
            projectGradleManager = projectGradleManager,
            moduleGradleManager = moduleGradleManager
        )
    }

    companion object : ParentInstanceCompanion(GradlePackage) {
        override fun createInstance(virtualFile: VirtualFile) = ProjectPackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(ProjectAppPackage, ProjectGradleManager, GradlePackage)
    }
}