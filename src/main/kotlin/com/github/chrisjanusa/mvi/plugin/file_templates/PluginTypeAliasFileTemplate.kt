package com.github.chrisjanusa.mvi.plugin.file_templates

import com.github.chrisjanusa.mvi.file_managment.toPascalCase
import com.github.chrisjanusa.mvi.foundation.FileTemplate
import com.github.chrisjanusa.mvi.foundation.addIf

internal class PluginTypeAliasFileTemplate(private val featureName: String, private val pluginName: String, private val hasState: Boolean, private val hasSlice: Boolean) : FileTemplate("${pluginName.toPascalCase()}TypeAlias") {
    override fun createContent(rootPackage: String): String {
        val pluginCapitalized = pluginName.toPascalCase()
        val state = if (hasState) "${pluginCapitalized}State" else "NoState"
        val slice = if (hasSlice) "${pluginCapitalized}Slice" else "NoSlice"
        return "import $rootPackage.foundation.ActionEffect\n" +
                "import $rootPackage.foundation.Effect\n" +
                "import $rootPackage.foundation.NavEffect\n" +
                "import $rootPackage.foundation.StateEffect\n" +
                "import $rootPackage.foundation.StateSliceEffect\n" +
                "import $rootPackage.$featureName.plugin.$pluginName.$slice\n".addIf { hasSlice } +
                "import $rootPackage.foundation.state.NoSlice\n".addIf { !hasSlice } +
                "import $rootPackage.$featureName.plugin.$pluginName.$state\n".addIf { hasState } +
                "import $rootPackage.foundation.state.NoState\n".addIf { !hasState } +
                "\n" +
                "internal abstract class ${pluginCapitalized}NavEffect: NavEffect<$state, $slice>()\n" +
                "\n" +
                "internal typealias ${pluginCapitalized}Effect = Effect<$state, $slice>\n" +
                "\n" +
                "internal typealias ${pluginCapitalized}StateEffect = StateEffect<$state, $slice>\n" +
                "\n" +
                "internal typealias ${pluginCapitalized}StateSliceEffect = StateSliceEffect<$state, $slice>\n" +
                "\n" +
                "internal typealias ${pluginCapitalized}ActionEffect = ActionEffect<$state, $slice>\n"
    }
}