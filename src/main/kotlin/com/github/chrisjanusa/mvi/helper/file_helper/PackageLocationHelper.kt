package com.github.chrisjanusa.mvi.helper.file_helper

import com.github.chrisjanusa.mvi.package_structure.getManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.FeaturePackage
import com.github.chrisjanusa.mvi.package_structure.manager.feature.service.FeatureServicePackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.FeatureChild
import com.github.chrisjanusa.mvi.package_structure.parent_provider.FeatureDirectChild
import com.github.chrisjanusa.mvi.package_structure.parent_provider.ServiceChild
import com.intellij.openapi.actionSystem.AnActionEvent
import com.github.chrisjanusa.mvi.package_structure.manager.RootPackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootDirectChild

fun AnActionEvent.isRootPackageOrDirectChild(): Boolean {
    val manager = getManager()
    return manager is RootDirectChild || manager is RootPackage
}

fun AnActionEvent.isInsideServicePackage(): Boolean {
    val manager = getManager()
    return manager is ServiceChild || manager is FeatureServicePackage
}

fun AnActionEvent.isInsideFeaturePackage(): Boolean {
    val manager = getManager()
    return manager is FeatureChild || manager is FeaturePackage
}

fun AnActionEvent.isFeaturePackageOrDirectChild(): Boolean {
    val manager = getManager()
    return manager is FeatureDirectChild || manager is FeaturePackage
}