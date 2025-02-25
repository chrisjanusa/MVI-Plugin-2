package com.github.chrisjanusa.mvi.package_structure.manager.project.gradle

import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.manager.project.library.Library
import com.github.chrisjanusa.mvi.package_structure.manager.project.library.LibraryPlugin
import com.intellij.openapi.vfs.VirtualFile

class LibsManager(
    file: VirtualFile,
) : FileManager(file) {
    fun addLibraryGroupVersion(libraryGroupName: String, version: String): Boolean {
        val documentText = documentLines.toMutableList()
        val libraryGroupNameText = "$libraryGroupName = \""
        if (!documentText.any { it.contains(libraryGroupNameText) }) {
            run {
                documentText.forEachIndexed { index, line ->
                    if (line.contains("[versions]")) {
                        documentText.add(index + 1, "$libraryGroupNameText$version\"")
                        return@run
                    }
                }
            }
            documentLines = documentText
            return true
        }
        return false
    }

    fun addLibraryToLib(library: Library, libraryGroupName: String) {
        val documentText = documentLines.toMutableList()
        if (!documentText.any { it.contains(library.libraryModule) }) {
            run {
                documentText.forEachIndexed { index, line ->
                    if (line.contains("[libraries]")) {
                        documentText.add(
                            index + 1,
                            "$libraryGroupName-${library.libraryName} = { module = \"${library.libraryModule}\", version.ref = \"$libraryGroupName\" }"
                        )
                        return@run
                    }
                }
            }
            documentLines = documentText
        }
    }

    fun addLibraryGroupBundle(libraryGroupName: String, libraries: List<Library>, isTestGroup: Boolean = false, onSuccess: (String) -> Unit) {
        val documentText = documentLines.toMutableList()
        val bundleSuffix = if (isTestGroup) "-testing" else ""
        val bundleDefinition = "$libraryGroupName$bundleSuffix = ["
        if (!documentText.any { it.contains(bundleDefinition) }) {
            val bundleLines = mutableListOf(
                bundleDefinition
            )
            libraries.forEach { library ->
                bundleLines.add(
                    "    \"$libraryGroupName-${library.libraryName}\","
                )
            }
            bundleLines.add("]")
            val bundlesTag = "[bundles]"
            if (documentText.any { it.contains(bundlesTag) }) {
                run {
                    documentText.forEachIndexed { index, line ->
                        if (line.contains("[bundles]")) {
                            documentText.addAll(
                                index + 1,
                                bundleLines
                            )
                            return@run
                        }
                    }
                }
            } else {
                documentText.add("")
                documentText.add(bundlesTag)
                documentText.addAll(bundleLines)
            }
            documentLines = documentText
            val depSuffix = if (isTestGroup) ".testing" else ""
            onSuccess(depSuffix)
        }
    }

    fun addPluginLibrary(plugin: LibraryPlugin, libraryGroupName: String, onSuccess: () -> Unit) {
        val documentText = documentLines.toMutableList()
        if (!documentText.any { it.contains(plugin.pluginId) }) {
            run {
                documentText.forEachIndexed { index, line ->
                    if (line.contains("[plugins]")) {
                        documentText.add(
                            index + 1,
                            "$libraryGroupName-${plugin.pluginName} = { id = \"${plugin.pluginId}\", version.ref = \"$libraryGroupName\" }"
                        )
                        return@run
                    }
                }
            }
            documentLines = documentText
            onSuccess()
        }
    }

    companion object : StaticInstanceCompanion("libs.versions.toml") {
        override fun createInstance(virtualFile: VirtualFile) = LibsManager(virtualFile)
    }
}