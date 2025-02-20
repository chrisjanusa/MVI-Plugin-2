package com.github.chrisjanusa.mvi.plugin.file_templates

import com.github.chrisjanusa.mvi.file_managment.toPascalCase
import com.github.chrisjanusa.mvi.foundation.FileTemplate
import com.github.chrisjanusa.mvi.foundation.addIf

internal class PluginActionFileTemplate(private val pluginName: String, private val hasState: Boolean, private val hasSlice: Boolean) : FileTemplate("${pluginName.toPascalCase()}Action") {
    override fun createContent(rootPackage: String): String {
        val state = if (hasState) "${pluginName.toPascalCase()}State" else "NoState"
        val slice = if (hasSlice) "${pluginName.toPascalCase()}Slice" else "NoSlice"
        return "import $rootPackage.foundation.ReducibleAction\n" +
                "import $rootPackage.foundation.state.NoSlice\n".addIf { !hasSlice } +
                "import $rootPackage.foundation.state.NoState\n".addIf { !hasState } +
                "\n" +
                "\n" +
                "sealed class $fileName : ReducibleAction<$state, $slice> {\n" +
                "}\n"
    }
}