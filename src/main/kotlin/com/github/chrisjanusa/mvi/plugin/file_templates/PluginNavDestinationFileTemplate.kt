package com.github.chrisjanusa.mvi.plugin.file_templates

import com.github.chrisjanusa.mvi.file_managment.capitalize
import com.github.chrisjanusa.mvi.foundation.FileTemplate

internal class PluginNavDestinationFileTemplate(private val pluginName: String) : FileTemplate("${pluginName.capitalize()}NavDestination") {
    override fun createContent(rootPackage: String): String =
                "import androidx.compose.runtime.Composable\n" +
                        "import $rootPackage.foundation.nav.NavDestination\n" +
                        "import $rootPackage.foundation.viewmodel.ParentViewModel\n" +
                        "import $rootPackage.foundation.AppAction\n" +
                        "import $rootPackage.foundation.nav.NavComponentId\n" +
                        "import kotlinx.serialization.Serializable\n" +
                        "\n" +
                        "object $fileName : NavDestination(\n" +
                        "    componentClass = ${pluginName.capitalize()}NavComponentId::class\n" +
                        ") {\n" +
                        "    @Composable\n" +
                        "    override fun showDestinationContent(args: Any, onAppAction: (AppAction) -> Unit, parentViewModel: ParentViewModel?) {\n" +
                        "        ${pluginName.capitalize()}Plugin.PluginContent(onAppAction = onAppAction, parentViewModel = parentViewModel)\n" +
                        "    }\n" +
                        "}\n" +
                        "\n" +
                        "@Serializable\n" +
                        "data object ${pluginName.capitalize()}NavComponentId : NavComponentId"
}