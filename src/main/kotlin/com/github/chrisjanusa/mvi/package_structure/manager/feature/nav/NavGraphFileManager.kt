package com.github.chrisjanusa.mvi.package_structure.manager.feature.nav

import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticSuffixChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.ManagerProvider
import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.generated.PluginNavDestinationFileManager
import com.github.chrisjanusa.mvi.package_structure.parent_provider.FeatureChild
import com.intellij.openapi.vfs.VirtualFile

class NavGraphFileManager(actionFile: VirtualFile) : FileManager(actionFile), FeatureChild {
    val navPackage: FeatureNavPackage by lazy {
        FeatureNavPackage(file.parent)
    }
    override val featurePackage by lazy {
        navPackage.featurePackage
    }
    val featureName by lazy {
        navPackage.featureName
    }
    override val rootPackage by lazy {
        navPackage.rootPackage
    }

    fun addNavDestination(navDestination: PluginNavDestinationFileManager) {
        if (documentText.contains(navDestination.name)) return
        addImport(navDestination.packagePath)
        findAndReplace("// TODO add start destination", navDestination.name)
        addAfterFirst("        ${navDestination.name},") {
            it.contains("destinations = listOf(")
        }
        writeToDisk()
    }
    
    companion object : StaticSuffixChildInstanceCompanion("NavGraph", FeatureNavPackage) {
        override fun createInstance(virtualFile: VirtualFile) = NavGraphFileManager(virtualFile)
        fun getFileName(featureName: String) = "$featureName$SUFFIX"
        fun createNewInstance(insertionPackage: FeatureNavPackage): NavGraphFileManager? {
            val fileName = getFileName(insertionPackage.featureName)
            val navGraph = insertionPackage.createNewFile(
                fileName,
                NavGraphTemplate(insertionPackage, fileName)
                    .createContent()
            )?.let { NavGraphFileManager(it) }
            if (navGraph != null) {
                ManagerProvider.getAppActivity(navGraph)?.addNavGraph(navGraph)
            }
            return navGraph
        }
    }
}