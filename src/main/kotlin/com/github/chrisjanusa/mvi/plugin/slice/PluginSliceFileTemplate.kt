package com.github.chrisjanusa.mvi.plugin.slice

import com.github.chrisjanusa.mvi.file_managment.toPascalCase
import com.github.chrisjanusa.mvi.foundation.FileTemplate

internal class PluginSliceFileTemplate(pluginName: String) : FileTemplate("${pluginName.toPascalCase()}Slice") {
    override fun createContent(rootPackage: String): String =
                "import $rootPackage.foundation.state.Slice\n" +
                        "\n" +
                        "data class $fileName(\n" +
                        ") : Slice\n"
}