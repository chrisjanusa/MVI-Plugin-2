package com.github.chrisjanusa.mvi.plugin.file_templates

import com.github.chrisjanusa.mvi.file_managment.toPascalCase
import com.github.chrisjanusa.mvi.foundation.FileTemplate
import com.github.chrisjanusa.mvi.foundation.addIf

internal class PluginContentFileTemplate(private val featureName: String, private val pluginName: String, private val hasState: Boolean, private val hasSlice: Boolean) : FileTemplate("${pluginName.toPascalCase()}Content") {
    override fun createContent(rootPackage: String): String {
        val pluginCapitalized = pluginName.toPascalCase()
        val state = "${pluginCapitalized}State"
        val slice = "${pluginCapitalized}Slice"
        return "import androidx.compose.runtime.Composable\n" +
                "import androidx.compose.ui.Modifier\n" +
                "import $rootPackage.foundation.OnAction\n" +
                "import $rootPackage.$featureName.plugin.$pluginName.$slice\n".addIf { hasSlice } +
                "import $rootPackage.$featureName.plugin.$pluginName.$state\n".addIf { hasState } +
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