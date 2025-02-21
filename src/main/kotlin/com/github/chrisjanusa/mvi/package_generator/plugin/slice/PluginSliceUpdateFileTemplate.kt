package com.github.chrisjanusa.mvi.package_generator.plugin.slice

import com.github.chrisjanusa.mvi.helper.file_managment.getPluginPackageFile
import com.github.chrisjanusa.mvi.helper.file_creation.FileTemplate
import com.intellij.openapi.actionSystem.AnActionEvent

internal class PluginSliceUpdateFileTemplate(
    actionEvent: AnActionEvent,
    pluginPackage: String? = actionEvent.getPluginPackageFile()?.name
) : FileTemplate(
    actionEvent = actionEvent,
    pluginPackage = pluginPackage
) {
    override val fileName: String
        get() = "${pluginName}SliceUpdate"

    override fun createContent(): String =
                "import $rootPackage.foundation.SliceUpdate\n" +
                "import $rootPackage.$featurePackage.plugin.$pluginPackage.${pluginName}Slice\n" +
                "\n" +
                "data class ${fileName}(\n" +
                "    val slice: ${pluginName}Slice\n" +
                ") : SliceUpdate\n"
}