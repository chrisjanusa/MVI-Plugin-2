package com.github.chrisjanusa.mvi.package_generator.app.file_templates.foundation.state

import com.github.chrisjanusa.mvi.helper.file_creation.FileTemplate
import com.intellij.openapi.actionSystem.AnActionEvent

internal class StateFileTemplate(
    actionEvent: AnActionEvent,
    rootPackage: String
) : FileTemplate(
    actionEvent = actionEvent,
    rootPackage = rootPackage
) {
    override val fileName: String
        get() = "State"

    override fun createContent(): String =
                "interface State\n" +
                        "\n" +
                        "object NoState : State\n"
}