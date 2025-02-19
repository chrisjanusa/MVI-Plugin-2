package com.github.chrisjanusa.mvi.plugin.file_templates

import com.github.chrisjanusa.mvi.file_managment.capitalize
import com.github.chrisjanusa.mvi.foundation.FileTemplate

internal class PluginEffectFileTemplate(pluginName: String) : FileTemplate("${pluginName.capitalize()}Effect") {
    override fun createContent(rootPackage: String): String =
                ""
}