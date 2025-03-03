package com.github.chrisjanusa.mvi.package_structure.manager.project.manifest

import com.github.chrisjanusa.mvi.helper.file_helper.toPascalCase
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.app.AppActivityFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.intellij.openapi.vfs.VirtualFile

class ManifestManager(
    file: VirtualFile,
) : FileManager(file) {

    fun updateActivityName(appActivityFileManager: AppActivityFileManager) {
        val newPath = appActivityFileManager.packagePath.substringAfter(appActivityFileManager.rootPackage.packagePath)
        findAndReplace(".MainActivity", newPath)
    }

    fun addApplication(appName: String) {
        if (documentText.contains("android:name=\".app.${appName.toPascalCase()}Application\"")) return
        addAfterFirst("        android:name=\".app.${appName.toPascalCase()}Application\"") { line ->
            line.contains("<application")
        }
    }

    fun addPermission(permissionName: String) {
        if (documentText.contains("<uses-permission android:name=\"${permissionName}\"/>")) return

        addBeforeFirst("    <uses-permission android:name=\"${permissionName}\"/>\n") { line ->
            line.contains("<application")
        }
    }

    companion object : StaticInstanceCompanion("AndroidManifest.xml") {
        override fun createInstance(virtualFile: VirtualFile) = ManifestManager(virtualFile)
        val PATH_FROM_PROJECT = "app/src/main/$NAME"
    }
}