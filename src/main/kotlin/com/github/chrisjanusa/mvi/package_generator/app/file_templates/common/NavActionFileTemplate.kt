package com.github.chrisjanusa.mvi.package_generator.app.file_templates.common

import com.github.chrisjanusa.mvi.helper.file_creation.FileTemplate
import com.intellij.openapi.actionSystem.AnActionEvent

internal class NavActionFileTemplate(
    actionEvent: AnActionEvent,
    rootPackage: String
) : FileTemplate(
    actionEvent = actionEvent,
    rootPackage = rootPackage
) {
    override val fileName: String
        get() = "CoreNavAction"

    override fun createContent(): String =
                "import $rootPackage.foundation.NavAction\n" +
                "\n" +
                "sealed class CoreNavAction : NavAction {\n" +
                "    data object OnBackClick : NavAction\n" +
                "}"
}