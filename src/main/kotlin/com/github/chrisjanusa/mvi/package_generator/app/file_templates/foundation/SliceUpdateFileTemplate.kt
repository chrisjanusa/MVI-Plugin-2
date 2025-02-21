package com.github.chrisjanusa.mvi.package_generator.app.file_templates.foundation

import com.github.chrisjanusa.mvi.helper.file_creation.FileTemplate
import com.intellij.openapi.actionSystem.AnActionEvent

internal class SliceUpdateFileTemplate(
    actionEvent: AnActionEvent,
    rootPackage: String
) : FileTemplate(
    actionEvent = actionEvent,
    rootPackage = rootPackage
) {
    override val fileName: String
        get() = "SliceUpdate"

    override fun createContent(): String =
                "interface SliceUpdate\n"
}