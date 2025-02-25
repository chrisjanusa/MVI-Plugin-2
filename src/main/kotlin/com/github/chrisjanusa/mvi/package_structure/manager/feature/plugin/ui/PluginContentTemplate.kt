package com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.ui

import com.github.chrisjanusa.mvi.package_structure.Manager
import com.github.chrisjanusa.mvi.package_structure.manager.base.Template
import com.github.chrisjanusa.mvi.package_structure.manager.base.addIf

internal class PluginContentTemplate(
    private val hasState: Boolean,
    private val hasSlice: Boolean,
    packageManager: Manager,
    fileName: String
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {

    override fun createContent(): String {
        val state = pluginPackage?.state?.name ?: "plugin not found"
        val slice = pluginPackage?.slice?.name ?: "plugin not found"
        return "import androidx.compose.runtime.Composable\n" +
                "import androidx.compose.ui.Modifier\n" +
                "import $rootPackagePath.foundation.OnAction\n" +
                "import $rootPackagePath.$featurePackageName.plugin.$pluginPackageName.$slice\n".addIf { hasSlice } +
                "import $rootPackagePath.$featurePackageName.plugin.$pluginPackageName.$state\n".addIf { hasState } +
                "\n" +
                "@Composable\n" +
                "internal fun $fileName(\n" +
                "    modifier: Modifier,\n" +
                "    state: $state,\n".addIf { hasState } +
                "    slice: $slice,\n".addIf { hasSlice } +
                "    onAction: OnAction,\n" +
                ") {\n" +
                "\n" +
                "}\n"
    }
}