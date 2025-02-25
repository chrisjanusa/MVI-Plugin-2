package com.github.chrisjanusa.mvi.helper.file_helper

import com.github.chrisjanusa.mvi.package_structure.getManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.FeaturePackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.FeatureChild
import com.github.chrisjanusa.mvi.package_structure.parent_provider.FeatureDirectChild
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.vfs.VirtualFile

fun AnActionEvent.isInsideFeaturePackage(): Boolean {
    val manager = getManager()
    return manager is FeatureChild || manager is FeaturePackage
}

fun AnActionEvent.isFeaturePackageOrDirectChild(): Boolean {
    val manager = getManager()
    return manager is FeatureDirectChild || manager is FeaturePackage
}

fun AnActionEvent.getFeaturePackageFile(): VirtualFile? {
    return findParentOfFile("plugin") ?: findParentOfFile("service") ?: findParentOfFile("domain_model")
}
