package com.github.chrisjanusa.mvi.package_generator.plugin.file_templates

import com.github.chrisjanusa.mvi.helper.file_managment.getPluginPackageFile
import com.github.chrisjanusa.mvi.helper.file_creation.FileTemplate
import com.github.chrisjanusa.mvi.helper.file_creation.addIf
import com.intellij.openapi.actionSystem.AnActionEvent

internal class PluginTypeAliasFileTemplate(
    private val hasState: Boolean,
    private val hasSlice: Boolean,
    actionEvent: AnActionEvent,
    pluginPackage: String? = actionEvent.getPluginPackageFile()?.name
) : FileTemplate(
    actionEvent = actionEvent,
    pluginPackage = pluginPackage
) {
    override val fileName: String
        get() = "${pluginName}TypeAlias"

    override fun createContent(): String {
        val state = if (hasState) "${pluginName}State" else "NoState"
        val slice = if (hasSlice) "${pluginName}Slice" else "NoSlice"
        return "import $rootPackage.foundation.ActionEffect\n" +
                "import $rootPackage.foundation.Effect\n" +
                "import $rootPackage.foundation.NavEffect\n" +
                "import $rootPackage.foundation.StateEffect\n" +
                "import $rootPackage.foundation.StateSliceEffect\n" +
                "import $rootPackage.$featureName.plugin.$pluginPackage.$slice\n".addIf { hasSlice } +
                "import $rootPackage.foundation.state.NoSlice\n".addIf { !hasSlice } +
                "import $rootPackage.$featureName.plugin.$pluginPackage.$state\n".addIf { hasState } +
                "import $rootPackage.foundation.state.NoState\n".addIf { !hasState } +
                "\n" +
                "internal abstract class ${pluginName}NavEffect: NavEffect<$state, $slice>()\n" +
                "\n" +
                "internal typealias ${pluginName}Effect = Effect<$state, $slice>\n" +
                "\n" +
                "internal typealias ${pluginName}StateEffect = StateEffect<$state, $slice>\n" +
                "\n" +
                "internal typealias ${pluginName}StateSliceEffect = StateSliceEffect<$state, $slice>\n" +
                "\n" +
                "internal typealias ${pluginName}ActionEffect = ActionEffect<$state, $slice>\n"
    }
}