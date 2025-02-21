package com.github.chrisjanusa.mvi.package_generator.app.file_templates.foundation.nav

import com.github.chrisjanusa.mvi.helper.file_creation.FileTemplate
import com.intellij.openapi.actionSystem.AnActionEvent

internal class NavComponentIdFileTemplate(
    actionEvent: AnActionEvent,
    rootPackage: String
) : FileTemplate(
    actionEvent = actionEvent,
    rootPackage = rootPackage
) {
    override val fileName: String
        get() = "NavComponentId"

    override fun createContent(): String =
                "internal interface NavComponentId\n"
}