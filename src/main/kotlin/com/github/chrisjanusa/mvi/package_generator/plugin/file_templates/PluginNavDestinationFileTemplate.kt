package com.github.chrisjanusa.mvi.package_generator.plugin.file_templates

import com.github.chrisjanusa.mvi.helper.file_managment.getPluginPackageFile
import com.github.chrisjanusa.mvi.helper.file_creation.FileTemplate
import com.intellij.openapi.actionSystem.AnActionEvent

internal class PluginNavDestinationFileTemplate(
    actionEvent: AnActionEvent,
    pluginPackage: String? = actionEvent.getPluginPackageFile()?.name
) : FileTemplate(
    actionEvent = actionEvent,
    pluginPackage = pluginPackage
) {
    override val fileName: String
        get() = "${pluginName}NavDestination"

    override fun createContent(): String {
        return "import androidx.compose.runtime.Composable\n" +
               "import $rootPackage.foundation.nav.NavDestination\n" +
               "import $rootPackage.foundation.viewmodel.ParentViewModel\n" +
               "import $rootPackage.foundation.AppAction\n" +
               "import $rootPackage.foundation.nav.NavComponentId\n" +
               "import kotlinx.serialization.Serializable\n" +
               "\n" +
               "object $fileName : NavDestination(\n" +
               "    componentClass = $fileName NavComponentId::class\n" +
               ") {\n" +
               "    @Composable\n" +
               "    override fun showDestinationContent(args: Any, onAppAction: (AppAction) -> Unit, parentViewModel: ParentViewModel?) {\n" +
               "        ${pluginName}Plugin.PluginContent(onAppAction = onAppAction, parentViewModel = parentViewModel)\n" +
               "    }\n" +
               "}\n" +
               "\n" +
               "@Serializable\n" +
               "data object $fileName NavComponentId : NavComponentId"
    }
}