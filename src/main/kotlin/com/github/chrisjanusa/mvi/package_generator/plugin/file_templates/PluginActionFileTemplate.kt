package com.github.chrisjanusa.mvi.package_generator.plugin.file_templates

import com.github.chrisjanusa.mvi.helper.file_managment.getPluginPackageFile
import com.github.chrisjanusa.mvi.helper.file_creation.FileTemplate
import com.github.chrisjanusa.mvi.helper.file_creation.addIf
import com.intellij.openapi.actionSystem.AnActionEvent

internal class PluginActionFileTemplate(
    private val hasState: Boolean,
    private val hasSlice: Boolean,
    actionEvent: AnActionEvent,
    pluginPackage: String? = actionEvent.getPluginPackageFile()?.name
) : FileTemplate(
    actionEvent = actionEvent,
    pluginPackage = pluginPackage
) {
    override val fileName: String
        get() = "${pluginName}Action"

    override fun createContent(): String {
        val state = if (hasState) "${pluginName}State" else "NoState"
        val slice = if (hasSlice) "${pluginName}Slice" else "NoSlice"
        return "import $rootPackage.foundation.ReducibleAction\n" +
                "import $rootPackage.foundation.state.NoSlice\n".addIf { !hasSlice } +
                "import $rootPackage.foundation.state.NoState\n".addIf { !hasState } +
                "\n" +
                "\n" +
                "sealed class $fileName : ReducibleAction<$state, $slice> {\n" +
                "}\n"
    }
}