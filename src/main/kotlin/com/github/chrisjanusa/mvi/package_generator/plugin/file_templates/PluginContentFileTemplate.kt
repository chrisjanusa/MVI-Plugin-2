package com.github.chrisjanusa.mvi.package_generator.plugin.file_templates

import com.github.chrisjanusa.mvi.helper.file_managment.getPluginPackageFile
import com.github.chrisjanusa.mvi.helper.file_creation.FileTemplate
import com.github.chrisjanusa.mvi.helper.file_creation.addIf
import com.intellij.openapi.actionSystem.AnActionEvent

internal class PluginContentFileTemplate(
    private val hasState: Boolean,
    private val hasSlice: Boolean,
    actionEvent: AnActionEvent,
    pluginPackage: String? = actionEvent.getPluginPackageFile()?.name
) : FileTemplate(
    actionEvent = actionEvent,
    pluginPackage = pluginPackage
) {
    override val fileName: String
        get() = "${pluginName}Content"

    override fun createContent(): String {
        val state = "${pluginName}State"
        val slice = "${pluginName}Slice"
        return "import androidx.compose.runtime.Composable\n" +
                "import androidx.compose.ui.Modifier\n" +
                "import $rootPackage.foundation.OnAction\n" +
                "import $rootPackage.$featurePackage.plugin.$pluginPackage.$slice\n".addIf { hasSlice } +
                "import $rootPackage.$featurePackage.plugin.$pluginPackage.$state\n".addIf { hasState } +
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