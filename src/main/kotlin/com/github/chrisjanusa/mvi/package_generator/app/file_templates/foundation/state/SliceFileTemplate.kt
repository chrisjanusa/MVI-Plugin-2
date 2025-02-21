package com.github.chrisjanusa.mvi.package_generator.app.file_templates.foundation.state

import com.github.chrisjanusa.mvi.helper.file_creation.FileTemplate
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