package com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.generated

import com.github.chrisjanusa.mvi.package_structure.Manager
import com.github.chrisjanusa.mvi.package_structure.manager.base.Template
import com.github.chrisjanusa.mvi.package_structure.manager.base.addIf
import com.github.chrisjanusa.mvi.package_structure.manager.foundation.FoundationSliceFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.foundation.FoundationStateFileManager

internal class PluginClassTemplate(
    private val hasState: Boolean,
    private val hasSlice: Boolean,
    packageManager: Manager,
    fileName: String
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {

    override fun createContent(): String {
        val state = if (hasState) { pluginPackage?.state?.name ?: "plugin not found" } else FoundationStateFileManager.NO_STATE
        val slice = if (hasSlice) { pluginPackage?.slice?.name ?: "plugin not found" } else FoundationSliceFileManager.NO_SLICE
        return "import androidx.compose.runtime.Composable\n" +
                "import androidx.compose.ui.Modifier\n" +
                "import ${rootPackage?.commonPackage?.classNameHelper?.packagePathExcludingFile}.getClassName\n" +
                "import ${pluginPackage?.uiPackage?.content?.packagePath}\n" +
                "import ${foundationPackage?.action?.packagePathExcludingFile}.OnAction\n" +
                "import ${foundationPackage?.plugin?.packagePath}\n" +
                "import $rootPackagePath.$featurePackageName.plugin.$pluginPackageName.$slice\n".addIf { hasSlice } +
                "import ${foundationPackage?.slice?.packagePathExcludingFile}.NoSlice\n".addIf { !hasSlice } +
                "import $rootPackagePath.$featurePackageName.plugin.$pluginPackageName.$state\n".addIf { hasState } +
                "import ${foundationPackage?.state?.packagePathExcludingFile}.NoState\n".addIf { !hasState } +
                "\n" +
                "object $fileName : Plugin<$state, $slice>(getClassName<${pluginName}ViewModel>()) {\n" +
                "    @Composable\n" +
                "    override fun Content(modifier: Modifier, state: $state, slice: $slice, onAction: OnAction) {\n" +
                "        ${pluginName}Content(\n" +
                "            modifier = modifier,\n" +
                "            state = state,\n".addIf { hasState } +
                "            slice = slice,\n".addIf { hasSlice } +
                "            onAction = onAction\n" +
                "        )\n" +
                "    }\n" +
                "}\n"
    }
}