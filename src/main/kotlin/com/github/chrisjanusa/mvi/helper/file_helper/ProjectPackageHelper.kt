package com.github.chrisjanusa.mvi.helper.file_helper

import com.github.chrisjanusa.mvi.package_structure.manager.project.ProjectPackage
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectLocator
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile

fun VirtualFile.getProject() : Project? = ProjectLocator.getInstance().guessProjectForFile(this)

fun Project.getProjectPackage() : ProjectPackage? {
    val basePath = basePath
    if (basePath.isNullOrBlank()) {
        return null
    }
    return LocalFileSystem.getInstance().findFileByPath(basePath)?.let { ProjectPackage(it) }
}