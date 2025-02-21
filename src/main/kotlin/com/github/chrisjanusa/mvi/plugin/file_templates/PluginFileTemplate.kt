package com.github.chrisjanusa.mvi.plugin.file_templates

import com.github.chrisjanusa.mvi.file_managment.getPluginPackageFile
import com.github.chrisjanusa.mvi.foundation.FileTemplate
import com.github.chrisjanusa.mvi.foundation.addIf
import com.intellij.openapi.actionSystem.AnActionEvent

internal class PluginFileTemplate(
    private val hasState: Boolean,
    private val hasSlice: Boolean,
    actionEvent: AnActionEvent,
    pluginPackage: String? = actionEvent.getPluginPackageFile()?.name
) : FileTemplate(
    actionEvent = actionEvent,
    pluginPackage = pluginPackage
) {
    override val fileName: String
        get() = "${pluginName}Plugin"

    override fun createContent(): String {
        val state = if (hasState) "${pluginName}State" else "NoState"
        val slice = if (hasSlice) "${pluginName}Slice" else "NoSlice"
        return "import androidx.compose.runtime.Composable\n" +
                "import androidx.compose.ui.Modifier\n" +
                "import $rootPackage.common.helper.getClassName\n" +
                "import $rootPackage.$featurePackage.plugin.$pluginPackage.ui.${pluginName}Content\n" +
                "import $rootPackage.foundation.OnAction\n" +
                "import $rootPackage.foundation.Plugin\n" +
                "import $rootPackage.$featurePackage.plugin.$pluginPackage.$slice\n".addIf { hasSlice } +
                "import $rootPackage.foundation.state.NoSlice\n".addIf { !hasSlice } +
                "import $rootPackage.$featurePackage.plugin.$pluginPackage.$state\n".addIf { hasState } +
                "import $rootPackage.foundation.state.NoState\n".addIf { !hasState } +
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