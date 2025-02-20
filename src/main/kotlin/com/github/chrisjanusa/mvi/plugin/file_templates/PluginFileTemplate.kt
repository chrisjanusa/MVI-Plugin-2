package com.github.chrisjanusa.mvi.plugin.file_templates

import com.github.chrisjanusa.mvi.file_managment.toPascalCase
import com.github.chrisjanusa.mvi.foundation.FileTemplate
import com.github.chrisjanusa.mvi.foundation.addIf

internal class PluginFileTemplate(private val featureName: String, private val pluginName: String, private val hasState: Boolean, private val hasSlice: Boolean) : FileTemplate("${pluginName.toPascalCase()}Plugin") {
    override fun createContent(rootPackage: String): String {
        val pluginCapitalized = pluginName.toPascalCase()
        val state = if (hasState) "${pluginCapitalized}State" else "NoState"
        val slice = if (hasSlice) "${pluginCapitalized}Slice" else "NoSlice"
        return "import androidx.compose.runtime.Composable\n" +
                "import androidx.compose.ui.Modifier\n" +
                "import $rootPackage.common.helper.getClassName\n" +
                "import $rootPackage.$featureName.plugin.$pluginName.ui.${pluginCapitalized}Content\n" +
                "import $rootPackage.foundation.OnAction\n" +
                "import $rootPackage.foundation.Plugin\n" +
                "import $rootPackage.$featureName.plugin.$pluginName.$slice\n".addIf { hasSlice } +
                "import $rootPackage.foundation.state.NoSlice\n".addIf { !hasSlice } +
                "import $rootPackage.$featureName.plugin.$pluginName.$state\n".addIf { hasState } +
                "import $rootPackage.foundation.state.NoState\n".addIf { !hasState } +
                "\n" +
                "object $fileName : Plugin<$state, $slice>(getClassName<${pluginCapitalized}ViewModel>()) {\n" +
                "    @Composable\n" +
                "    override fun Content(modifier: Modifier, state: $state, slice: $slice, onAction: OnAction) {\n" +
                "        ${pluginCapitalized}Content(\n" +
                "            modifier = modifier,\n" +
                "            state = state,\n".addIf { hasState } +
                "            slice = slice,\n".addIf { hasSlice } +
                "            onAction = onAction\n" +
                "        )\n" +
                "    }\n" +
                "}\n"
    }
}