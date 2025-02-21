package com.github.chrisjanusa.mvi.helper.document_management.library

import com.github.chrisjanusa.mvi.helper.file_managment.getProjectDirFile
import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.ui.Messages
import javax.swing.SwingUtilities

fun AnActionEvent.getGradle(): Document? {
    val projectBaseDir = getProjectDirFile() ?: return null
    val gradleDir = projectBaseDir.findChild("app")
    val libsFile = gradleDir?.findChild("build.gradle.kts") ?: return null
    return FileDocumentManager.getInstance().getDocument(libsFile)
}

fun AnActionEvent.getLibs(): Document? {
    val projectBaseDir = getProjectDirFile() ?: return null
    val gradleDir = projectBaseDir.findChild("gradle")
    val libsFile = gradleDir?.findChild("libs.versions.toml") ?: return null
    return FileDocumentManager.getInstance().getDocument(libsFile)
}

fun AnActionEvent.getProjectGradle(): Document? {
    val projectBaseDir = getProjectDirFile()
    val gradleFile = projectBaseDir?.findChild("build.gradle.kts") ?: return null
    return FileDocumentManager.getInstance().getDocument(gradleFile)
}

fun syncProject(actionEvent: AnActionEvent) {
    val androidSyncAction = getAction("Android.SyncProject")
    val refreshAllProjectAction = getAction("ExternalSystem.RefreshAllProjects")
    if (androidSyncAction != null && androidSyncAction !is EmptyAction) {
        androidSyncAction.actionPerformed(actionEvent)
    } else if (refreshAllProjectAction != null && refreshAllProjectAction !is EmptyAction) {
        refreshAllProjectAction.actionPerformed(actionEvent)
    } else {
        SwingUtilities.invokeLater {
            Messages.showInfoMessage(
                "Project sync failed.",
                "SYNC FAILED"
            )
        }
    }
}

private fun getAction(actionId: String): AnAction? {
    return ActionManager.getInstance().getAction(actionId)
}


