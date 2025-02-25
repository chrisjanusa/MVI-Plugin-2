package com.github.chrisjanusa.mvi.package_structure.manager.project

import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.project.library.LibraryPlugin
import com.intellij.openapi.vfs.VirtualFile

class ProjectGradleManager(
    file: VirtualFile,
) : FileManager(file) {
    fun addPluginLibraryToGradle(plugin: LibraryPlugin, libraryGroupName: String) {
        val pluginDep = "alias(libs.plugins.$libraryGroupName.${plugin.pluginName})"
        val projectDocumentText = documentLines.toMutableList()
        if (!projectDocumentText.any { it.contains(pluginDep) }) {
            run {
                projectDocumentText.forEachIndexed { index, line ->
                    if (line.contains("plugins {")) {
                        if (plugin.apply) {
                            projectDocumentText.add(
                                index + 1,
                                "    $pluginDep"
                            )
                        } else {
                            projectDocumentText.add(
                                index + 1,
                                "    $pluginDep apply false"
                            )
                        }

                        return@run
                    }
                }
            }
            documentLines = projectDocumentText
        }
    }

    companion object : StaticChildInstanceCompanion("build.gradle.kts", ProjectPackage) {
        override fun createInstance(virtualFile: VirtualFile) = ProjectGradleManager(virtualFile)
    }
}