package com.github.chrisjanusa.mvi.library

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.Document

class LibraryManager(
    private val moduleBuildGradle: Document,
    private val libs: Document,
    private val projectBuildGradle: Document,
) {
    private var moduleGradleText = moduleBuildGradle.text
    private var libsText = libs.text
    private var projectGradleText = projectBuildGradle.text

    fun addLibraryGroup(libraryGroup: LibraryGroup) {
        addLibraryGroupVersion(libraryGroup.libraryGroupName, libraryGroup.version)
        libraryGroup.libraries.forEach {
            addLibraryToLib(it, libraryGroup.libraryGroupName)
        }
        libraryGroup.testLibraries.forEach {
            addLibraryToLib(it, libraryGroup.libraryGroupName)
        }
        if (libraryGroup.libraries.isNotEmpty()) {
            addLibraryGroupBundle(libraryGroup.libraryGroupName, libraryGroup.libraries)
        }
        if (libraryGroup.testLibraries.isNotEmpty()) {
            addLibraryGroupBundle(libraryGroup.libraryGroupName, libraryGroup.testLibraries, true)
        }
        libraryGroup.plugins.forEach {
            addPluginLibrary(it, libraryGroup.libraryGroupName)
        }
    }

    private fun addLibraryGroupVersion(libraryGroupName: String, version: String): Boolean {
        val documentText = libsText.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toMutableList()
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
            libsText = documentText.joinToString(separator = "\n")
            return true
        }
        return false
    }

    fun addLibrary(library: Library, libraryGroupName: String, isKsp: Boolean = false) {
        addLibraryToLib(library, libraryGroupName)
        if (isKsp) {
            addLibraryToGradle("ksp(libs.$libraryGroupName.${library.libraryName})")
        } else {
            addLibraryToGradle("implementation(libs.$libraryGroupName.${library.libraryName})")
        }
    }

    private fun addLibraryToLib(library: Library, libraryGroupName: String) {
        val documentText = libsText.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toMutableList()
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
            libsText = documentText.joinToString(separator = "\n")
        }
    }

    private fun addLibraryGroupBundle(libraryGroupName: String, libraries: List<Library>, isTestGroup: Boolean = false) {
        val documentText = libsText.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toMutableList()
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
            libsText = documentText.joinToString(separator = "\n")
            val depSuffix = if (isTestGroup) ".testing" else ""
            addLibraryBundleToGradle("$libraryGroupName$depSuffix")
        }
    }

    private fun addLibraryBundleToGradle(libraryGroupName: String) {
        addLibraryToGradle("implementation(libs.bundles.$libraryGroupName)")
    }

    private fun addLibraryToGradle(pluginDep: String) {
        val moduleDocumentText = moduleGradleText.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toMutableList()
        if (!moduleDocumentText.any { it.contains(pluginDep) }) {
            run {
                moduleDocumentText.forEachIndexed { index, line ->
                    if (line.contains("dependencies {")) {
                        moduleDocumentText.add(
                            index + 1,
                            "    $pluginDep"
                        )
                        return@run
                    }
                }
            }
            moduleGradleText = moduleDocumentText.joinToString(separator = "\n")
        }
    }

    fun addPluginLibrary(plugin: LibraryPlugin, libraryGroupName: String) {
        val documentText = libsText.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toMutableList()
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
            libsText = documentText.joinToString(separator = "\n")
            addPluginLibraryToGradle(plugin, libraryGroupName)
        }
    }

    private fun addPluginLibraryToGradle(plugin: LibraryPlugin, libraryGroupName: String) {
        val moduleDocumentText = moduleGradleText.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toMutableList()
        val pluginDep = "alias(libs.plugins.$libraryGroupName.${plugin.pluginName})"
        if (!moduleDocumentText.any { it.contains(pluginDep) }) {
            run {
                moduleDocumentText.forEachIndexed { index, line ->
                    if (line.contains("plugins {")) {
                        moduleDocumentText.add(
                            index + 1,
                            "    $pluginDep"
                        )
                        return@run
                    }
                }
            }
            moduleGradleText = moduleDocumentText.joinToString(separator = "\n")
        }
        val projectDocumentText = projectGradleText.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toMutableList()
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
            projectGradleText = projectDocumentText.joinToString(separator = "\n")
        }
    }

    fun writeToGradle() {
        val application = ApplicationManager.getApplication()
        application.invokeLater {
            application.runWriteAction {
                moduleBuildGradle.setText(moduleGradleText)
                projectBuildGradle.setText(projectGradleText)
                libs.setText(libsText)
            }
        }
    }
}

fun AnActionEvent.getLibraryManager(): LibraryManager? {
    val moduleGradle = getGradle() ?: return null
    val projectGradle = getProjectGradle() ?: return null
    val libs = getLibs() ?: return null
    return LibraryManager(
        moduleGradle,
        libs,
        projectGradle
    )
}