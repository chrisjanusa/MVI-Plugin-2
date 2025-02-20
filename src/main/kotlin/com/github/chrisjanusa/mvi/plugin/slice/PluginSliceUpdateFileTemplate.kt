package com.github.chrisjanusa.mvi.plugin.slice

import com.github.chrisjanusa.mvi.file_managment.toPascalCase
import com.github.chrisjanusa.mvi.foundation.FileTemplate

internal class PluginSliceUpdateFileTemplate(private val featureName: String, private val pluginName: String) : FileTemplate("${pluginName.toPascalCase()}SliceUpdate") {
    override fun createContent(rootPackage: String): String =
                "import $rootPackage.foundation.SliceUpdate\n" +
                "import $rootPackage.$featureName.plugin.$pluginName.${pluginName.toPascalCase()}Slice\n" +
                "\n" +
                "data class ${fileName}(\n" +
                "    val slice: ${pluginName.toPascalCase()}Slice\n" +
                ") : SliceUpdate\n"
}