package com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.generated

import com.github.chrisjanusa.mvi.package_structure.Manager
import com.github.chrisjanusa.mvi.package_structure.manager.base.Template

internal class PluginNavDestinationTemplate(
    packageManager: Manager,
    fileName: String
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {

    override fun createContent(): String {
        return "import androidx.compose.runtime.Composable\n" +
               "import ${foundationPackage?.navComponent?.packagePathExcludingFile}.NavDestination\n" +
               "import ${foundationPackage?.parentViewModel?.packagePath}\n" +
               "import ${foundationPackage?.action?.packagePathExcludingFile}.AppAction\n" +
               "import ${foundationPackage?.navComponentId?.packagePath}\n" +
               "import kotlinx.serialization.Serializable\n" +
               "\n" +
               "object $fileName : NavDestination(\n" +
               "    componentClass = ${fileName}Id::class\n" +
               ") {\n" +
               "    @Composable\n" +
               "    override fun showDestinationContent(args: Any, onAppAction: (AppAction) -> Unit, parentViewModel: ParentViewModel?) {\n" +
               "        ${pluginName}Plugin.PluginContent(onAppAction = onAppAction, parentViewModel = parentViewModel)\n" +
               "    }\n" +
               "}\n" +
               "\n" +
               "@Serializable\n" +
               "data object ${fileName}Id : ${foundationPackage?.navComponentId}"
    }
}