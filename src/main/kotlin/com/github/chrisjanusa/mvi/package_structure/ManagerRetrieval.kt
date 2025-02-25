package com.github.chrisjanusa.mvi.package_structure

import com.github.chrisjanusa.mvi.helper.file_helper.isValidFile
import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.RootPackage
import com.github.chrisjanusa.mvi.package_structure.manager.feature.FeaturePackage
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.PluginPackage
import com.github.chrisjanusa.mvi.package_structure.manager.project.ProjectPackage
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.vfs.VirtualFile

fun AnActionEvent.getManager(): Manager? {
    val currFile = getData(PlatformDataKeys.VIRTUAL_FILE) ?: return null
    return currFile.getManager()
}

fun VirtualFile.getManager() : Manager? {
    if (RootPackage.isInstance(this)) return RootPackage(this)
    val potentialManagers = parent.getChildrenInstanceCompanion()
    if (potentialManagers.isEmpty()) return null
    val managerInstanceCompanion  = potentialManagers.firstOrNull {
        it.isInstance(this)
    } ?: return null
    return managerInstanceCompanion.createInstance(this)
}

private fun VirtualFile.getChildrenInstanceCompanion(): List<InstanceCompanion> {
    if (ProjectPackage.isInstance(this)) return ProjectPackage.allChildrenInstanceCompanions
    if (RootPackage.isInstance(this)) return RootPackage.allChildrenInstanceCompanions
    val potentialManagers = parent.getChildrenInstanceCompanion()
    val managerInstanceCompanion  = potentialManagers.firstOrNull {
        it.isInstance(this)
    } ?: return emptyList()
    return managerInstanceCompanion.allChildrenInstanceCompanions
}

fun AnActionEvent.getManagerOfType(managerType: InstanceCompanion): Manager? {
    val currFile = getData(PlatformDataKeys.VIRTUAL_FILE) ?: return null
    return currFile.getManagerOfType(managerType)
}

fun AnActionEvent.getRootPackage(): RootPackage? = getManagerOfType(RootPackage) as? RootPackage
fun AnActionEvent.getFeaturePackage(): FeaturePackage? = getManagerOfType(FeaturePackage) as? FeaturePackage
fun AnActionEvent.getPluginPackage(): PluginPackage? = getManagerOfType(PluginPackage) as? PluginPackage

private fun VirtualFile.getManagerOfType(managerType: InstanceCompanion): Manager? {
    if (managerType.isInstance(this)) return managerType.createInstance(this)
    if (isValidFile())
        return parent.getManagerOfType(managerType)
    else
        return null
}



//private val appPackageManagers: List<InstanceCompanion> = listOf(
//    KoinInitFileManager,
//    KoinModuleFileManager,
//    AppNavEffectFileManager,
//    AppNavManagerFileManager,
//    AppActivityFileManager,
//    AppApplicationFileManager,
//    AppViewModelFileManager,
//    AppDiPackage,
//    AppPackage,
//    AppEffectPackage,
//    AppNavPackage
//)
//
//private val commonPackageManagers: List<InstanceCompanion> = listOf(
//    CommonHelperPackage,
//    CommonNavPackage,
//    CommonPackage,
//    ClassNameHelperFileManager,
//    CoreNavActionFileManager
//)
//
//private val featurePackageManagers: List<InstanceCompanion> = listOf(
//
//)

