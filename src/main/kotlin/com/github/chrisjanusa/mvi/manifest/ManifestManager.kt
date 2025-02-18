package com.github.chrisjanusa.mvi.manifest

import com.github.chrisjanusa.mvi.file_managment.capitalize
import com.github.chrisjanusa.mvi.file_managment.getProjectDirFile
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.vfs.findFile

class ManifestManager(
    private val manifest: Document,
) {
    private var manifestText = manifest.text

    fun addApplication(appName: String) {
        val documentText = manifestText.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toMutableList()
        documentText.forEachIndexed { index, line ->
            if (line.contains("<application")) {
                documentText.add(index+1, "      android:name=\".app.${appName.capitalize()}Application\"")
            }
        }
    }

    private fun addPermission(permissionName: String) {
        val documentText = manifestText.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toMutableList()
        documentText.forEachIndexed { index, line ->
            if (line.contains("<application")) {
                documentText.add(index, "  <uses-permission android:name=\"${permissionName}\"/>")
                documentText.add(index+1, "")
            }
        }
    }

    fun writeToGradle() {
        val application = ApplicationManager.getApplication()
        application.invokeLater {
            application.runWriteAction {
                manifest.setText(manifestText)
            }
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