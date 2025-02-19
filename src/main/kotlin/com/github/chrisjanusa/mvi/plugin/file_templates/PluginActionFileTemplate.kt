package com.github.chrisjanusa.mvi.plugin.file_templates

import com.github.chrisjanusa.mvi.file_managment.capitalize
import com.github.chrisjanusa.mvi.foundation.FileTemplate

internal class PluginActionFileTemplate(private val pluginName: String, private val hasState: Boolean, private val hasSlice: Boolean) : FileTemplate("${pluginName.capitalize()}Action") {
    override fun createContent(rootPackage: String): String {
        val state = if (hasState) "${pluginName.capitalize()}State" else "NoState"
        val slice = if (hasSlice) "${pluginName.capitalize()}Slice" else "NoSlice"
        return "import $rootPackage.foundation.ReducibleAction\n" +
                "import $rootPackage.foundation.state.NoSlice\n".addIf { !hasSlice } +
                "import $rootPackage.foundation.state.NoState\n".addIf { !hasState } +
                "\n" +
                "\n" +
                "sealed class $fileName : ReducibleAction<$state, $slice> {\n" +
                "}\n"
    }
}