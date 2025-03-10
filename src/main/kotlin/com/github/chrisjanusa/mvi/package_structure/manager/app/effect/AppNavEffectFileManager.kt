package com.github.chrisjanusa.mvi.package_structure.manager.app.effect

import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.base.ActionFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootChild
import com.intellij.openapi.vfs.VirtualFile

class AppNavEffectFileManager(
    file: VirtualFile,
) : FileManager(file), RootChild {
    val effectModule by lazy {
        AppEffectPackage(file.parent)
    }
    override val rootPackage by lazy {
        effectModule.rootPackage
    }

    fun addNavEffect(actionFile: ActionFileManager, actionName: String) {
        addToBottom(
            "internal object ${actionName}Effect : AppEffect {\n" +
            "    override suspend fun launchEffect(actionFlow: Flow<AppAction>, navManager: NavManager, onAction: OnAppAction) {\n" +
            "        actionFlow.filterIsInstance<${actionFile.navActionClass}.$actionName>().collectLatest {\n" +
            "            // TODO: Add navigation\n" +
            "        }\n" +
            "    }\n" +
            "}\n"
        )
        addImport("${actionFile.packagePathExcludingFile}.${actionFile.navActionClass}")
        writeToDisk()
    }

    companion object : StaticChildInstanceCompanion("NavEffect", AppEffectPackage) {
        override fun createInstance(virtualFile: VirtualFile) = AppNavEffectFileManager(virtualFile)
        fun createNewInstance(insertionPackage: AppEffectPackage): AppNavEffectFileManager? {
            return insertionPackage.createNewFile(
                NAME,
                AppNavEffectTemplate(insertionPackage, NAME)
                    .createContent()
            )?.let { AppNavEffectFileManager(it) }
        }
    }
}