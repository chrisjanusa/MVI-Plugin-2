package com.github.chrisjanusa.mvi.document_management.manifest

import com.github.chrisjanusa.mvi.document_management.DocumentManager
import com.github.chrisjanusa.mvi.file_managment.capitalize
import com.github.chrisjanusa.mvi.file_managment.getProjectDirFile
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.vfs.findFile

class ManifestManager(
    manifest: Document,
) : DocumentManager(manifest) {

    fun addApplication(appName: String) {
        addAfterFirst("      android:name=\".app.${appName.capitalize()}Application\"") { line ->
            line.contains("<application")
        }
    }

    private fun addPermission(permissionName: String) {
        addBeforeFirst("  <uses-permission android:name=\"${permissionName}\"/>\n") { line ->
            line.contains("<application")
        }
    }
}

private fun AnActionEvent.getManifest(): Document? {
    val projectBaseDir = getProjectDirFile() ?: return null
    val manifest = projectBaseDir.findFile("app/src/main/AndroidManifest.xml") ?: return null
    return FileDocumentManager.getInstance().getDocument(manifest)
}

fun AnActionEvent.getManifestManager(): ManifestManager? {
    val manifest = getManifest() ?: return null
    return ManifestManager(
        manifest
    )
}