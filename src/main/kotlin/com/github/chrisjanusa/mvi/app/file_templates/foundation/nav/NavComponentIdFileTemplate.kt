package com.github.chrisjanusa.mvi.app.file_templates.foundation.nav

import com.github.chrisjanusa.mvi.foundation.FileTemplate
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