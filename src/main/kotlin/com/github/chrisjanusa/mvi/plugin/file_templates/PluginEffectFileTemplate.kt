package com.github.chrisjanusa.mvi.plugin.file_templates

import com.github.chrisjanusa.mvi.file_managment.getPluginPackageFile
import com.github.chrisjanusa.mvi.foundation.FileTemplate
import com.intellij.openapi.actionSystem.AnActionEvent

internal class PluginEffectFileTemplate(
    actionEvent: AnActionEvent,
    pluginPackage: String? = actionEvent.getPluginPackageFile()?.name
) : FileTemplate(
    actionEvent = actionEvent,
    pluginPackage = pluginPackage
) {
    override val fileName: String
        get() = "${pluginName}Effect"

    override fun createContent(): String =
                ""
}