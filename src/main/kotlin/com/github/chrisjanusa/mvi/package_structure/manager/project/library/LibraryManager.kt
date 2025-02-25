package com.github.chrisjanusa.mvi.package_structure.manager.project.library

import com.github.chrisjanusa.mvi.package_structure.manager.project.ProjectGradleManager
import com.github.chrisjanusa.mvi.package_structure.manager.project.app.ModuleGradleManager
import com.github.chrisjanusa.mvi.package_structure.manager.project.gradle.LibsManager

class LibraryManager(
    private val moduleGradleManager: ModuleGradleManager,
    private val projectGradleManager: ProjectGradleManager,
    private val libsManager: LibsManager,
) {
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
        return libsManager.addLibraryGroupVersion(libraryGroupName, version)
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
        libsManager.addLibraryToLib(library, libraryGroupName)
    }

    private fun addLibraryGroupBundle(libraryGroupName: String, libraries: List<Library>, isTestGroup: Boolean = false) {
        libsManager.addLibraryGroupBundle(libraryGroupName, libraries, isTestGroup) { depSuffix ->
            addLibraryBundleToGradle("$libraryGroupName$depSuffix")
        }
    }

    private fun addLibraryBundleToGradle(libraryGroupName: String) {
        addLibraryToGradle("implementation(libs.bundles.$libraryGroupName)")
    }

    private fun addLibraryToGradle(pluginDep: String) {
        moduleGradleManager.addLibraryToGradle(pluginDep)
    }

    fun addPluginLibrary(plugin: LibraryPlugin, libraryGroupName: String) {
        libsManager.addPluginLibrary(plugin, libraryGroupName) {
            addPluginLibraryToGradle(plugin, libraryGroupName)
        }
    }

    private fun addPluginLibraryToGradle(plugin: LibraryPlugin, libraryGroupName: String) {
        moduleGradleManager.addPluginLibraryToGradle(plugin, libraryGroupName)
        projectGradleManager.addPluginLibraryToGradle(plugin, libraryGroupName)
    }

    fun writeToGradle() {
        moduleGradleManager.writeToDisk()
        projectGradleManager.writeToDisk()
        libsManager.writeToDisk()
    }
}