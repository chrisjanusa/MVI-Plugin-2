package com.github.chrisjanusa.mvi.package_structure.manager.project.manifest

import com.github.chrisjanusa.mvi.helper.file_helper.toPascalCase
import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticInstanceCompanion
import com.intellij.openapi.vfs.VirtualFile

class ManifestManager(
    file: VirtualFile,
) : FileManager(file) {

    fun addApplication(appName: String) {
        addAfterFirst("      android:name=\".app.${appName.toPascalCase()}Application\"") { line ->
            line.contains("<application")
        }
    }

    private fun addPermission(permissionName: String) {
        addBeforeFirst("  <uses-permission android:name=\"${permissionName}\"/>\n") { line ->
            line.contains("<application")
        }
    }

    companion object : StaticInstanceCompanion("AndroidManifest.xml") {
        override fun createInstance(virtualFile: VirtualFile) = ManifestManager(virtualFile)
        val PATH_FROM_PROJECT = "app/src/main/$NAME"
    }
}