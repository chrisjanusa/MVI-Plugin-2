package com.github.chrisjanusa.mvi.plugin.slice

import com.github.chrisjanusa.mvi.file_managment.getPluginPackageFile
import com.github.chrisjanusa.mvi.foundation.FileTemplate
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