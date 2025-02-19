package com.github.chrisjanusa.mvi.plugin.file_templates

import com.github.chrisjanusa.mvi.file_managment.capitalize
import com.github.chrisjanusa.mvi.foundation.FileTemplate

internal class PluginStateFileTemplate(pluginName: String) : FileTemplate("${pluginName.capitalize()}State") {
    override fun createContent(rootPackage: String): String =
                "import $rootPackage.foundation.state.State\n" +
                        "\n" +
                        "data class $fileName(\n" +
                        ") : State\n"
}