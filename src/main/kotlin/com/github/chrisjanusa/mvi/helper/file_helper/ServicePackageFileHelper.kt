package com.github.chrisjanusa.mvi.helper.file_helper

import com.github.chrisjanusa.mvi.package_structure.getManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.service.ServicePackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.ServiceChild
import com.intellij.openapi.actionSystem.AnActionEvent

fun AnActionEvent.isInsideServicePackage(): Boolean {
    val manager = getManager()
    return manager is ServiceChild || manager is ServicePackage
}
