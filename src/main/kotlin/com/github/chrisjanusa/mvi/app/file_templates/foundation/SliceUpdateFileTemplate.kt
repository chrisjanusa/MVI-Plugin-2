package com.github.chrisjanusa.mvi.app.file_templates.foundation

import com.github.chrisjanusa.mvi.foundation.FileTemplate
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