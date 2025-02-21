package com.github.chrisjanusa.mvi.app.file_templates.foundation.state

import com.github.chrisjanusa.mvi.foundation.FileTemplate
import com.intellij.openapi.actionSystem.AnActionEvent

internal class SliceFileTemplate(
    actionEvent: AnActionEvent,
    rootPackage: String
) : FileTemplate(
    actionEvent = actionEvent,
    rootPackage = rootPackage
) {
    override val fileName: String
        get() = "Slice"

    override fun createContent(): String =
                "interface Slice\n" +
                        "\n" +
                        "object NoSlice : Slice\n"
}