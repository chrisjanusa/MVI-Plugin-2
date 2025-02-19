package com.github.chrisjanusa.mvi.plugin.slice

import com.github.chrisjanusa.mvi.file_managment.capitalize
import com.github.chrisjanusa.mvi.foundation.FileTemplate

internal class PluginSliceUpdateFileTemplate(private val featureName: String, private val pluginName: String) : FileTemplate("${pluginName.capitalize()}SliceUpdate") {
    override fun createContent(rootPackage: String): String =
                "import $rootPackage.foundation.SliceUpdate\n" +
                "import $rootPackage.$featureName.plugin.$pluginName.${pluginName.capitalize()}Slice\n" +
                "\n" +
                "data class ${fileName}(\n" +
                "    val slice: ${pluginName.capitalize()}Slice\n" +
                ") : SliceUpdate\n"
}