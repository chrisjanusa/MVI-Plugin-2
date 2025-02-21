package com.github.chrisjanusa.mvi.package_generator.plugin.slice

import com.github.chrisjanusa.mvi.helper.file_managment.getPluginPackageFile
import com.github.chrisjanusa.mvi.helper.file_creation.FileTemplate
import com.intellij.openapi.actionSystem.AnActionEvent

internal class PluginSliceFileTemplate(
    actionEvent: AnActionEvent,
    pluginPackage: String? = actionEvent.getPluginPackageFile()?.name
) : FileTemplate(
    actionEvent = actionEvent,
    pluginPackage = pluginPackage
) {
    override val fileName: String
        get() = "${pluginName}Slice"

    override fun createContent(): String =
                "import $rootPackage.foundation.state.Slice\n" +
                        "\n" +
                        "data class $fileName(\n" +
                        ") : Slice\n"
}