package com.github.chrisjanusa.mvi.package_structure.manager.app

import com.github.chrisjanusa.mvi.package_structure.manager.ManagerProvider
import com.github.chrisjanusa.mvi.package_structure.manager.app.helper.AppFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.app.helper.AppFileNameProvider
import com.github.chrisjanusa.mvi.package_structure.manager.app.helper.AppNameProvider
import com.github.chrisjanusa.mvi.package_structure.manager.app.helper.IAppFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.nav.NavGraphFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.shared.generated.FeatureSharedViewModelFileManager
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootChild
import com.intellij.openapi.vfs.VirtualFile

class AppActivityFileManager(
    file: VirtualFile,
    appFileManager: AppFileManager = AppFileManager(file)
) : IAppFileManager by appFileManager, FileManager(file), RootChild, AppNameProvider {
    override val appName: String by lazy {
        name.substringBefore(SUFFIX)
    }

    fun addNavGraph(navGraph: NavGraphFileManager) {
        if (documentText.contains("${navGraph.name}.createNavGraph")) return
        if (!documentText.contains("ComponentId")) {
            addImport("${navGraph.packagePath}ComponentId")
            findAndReplace("// TODO: Set initial destination", "${navGraph.name}ComponentId")
        }
        addImport(navGraph.packagePath)
        performActionAtFirst({it.contains("startDestination = ")}) { newLines, index ->
            newLines.add(
                index+2,
                "            ${navGraph.name}.createNavGraph(\n" +
                "                navGraphBuilder = this,\n" +
                "                navHostController = navController,\n" +
                "                onAppAction = { viewModel.onAction(it) },\n" +
                "            )\n"
            )
        }
        writeToDisk()
    }

    fun removeNavGraph(navGraph: NavGraphFileManager) {
        if (!documentText.contains("${navGraph.name}.createNavGraph")) return
        if (documentText.contains("${navGraph.name}ComponentId")) {
            removeImport("${navGraph.packagePath}ComponentId")
            findAndReplace("${navGraph.name}ComponentId", "// TODO: Set initial destination")
        }
        removeImport(navGraph.packagePath)
        removeBetween(
            startPredicate = { it.contains("${navGraph.name}.createNavGraph(") },
            endPredicate = { it.contains(")") },
        )
        writeToDisk()
    }

    fun addSharedViewModelToGraph(navGraph: NavGraphFileManager, sharedViewModel: FeatureSharedViewModelFileManager) {
        if (documentText.contains("getClassName<$sharedViewModel>()")) return
        navGraph.rootPackage.commonPackage?.classNameHelper?.functionPackagePath?.let { addImport(it) }
        addImport(sharedViewModel.packagePath)
        performActionAtFirst({it.contains("$navGraph.createNavGraph(")}) { newLines, index ->
            newLines.add(
                index+4,
                "                parentSharedViewModelName = getClassName<$sharedViewModel>()"
            )
        }
        writeToDisk()
    }

    fun removeSharedViewModel(sharedViewModel: FeatureSharedViewModelFileManager) {
        removeAllLinesThatMatch { it.contains("getClassName<$sharedViewModel>()") }
        writeToDisk()
    }

    companion object : AppFileNameProvider("Activity") {
        override fun createInstance(virtualFile: VirtualFile) = AppActivityFileManager(virtualFile)
        fun createNewInstance(insertionPackage: AppPackage, appName: String): AppActivityFileManager? {
            val fileName = getFileName(appName)
            val appActivity = insertionPackage.createNewFile(
                fileName,
                AppActivityTemplate(insertionPackage, fileName)
                    .createContent()
            )?.let { AppActivityFileManager(it) }
            if (appActivity != null) {
                ManagerProvider.setAppActivity(appActivity)
            }
            return appActivity
        }
    }
}