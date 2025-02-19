package com.github.chrisjanusa.mvi.app.file_templates.foundation.state

import com.github.chrisjanusa.mvi.foundation.FileTemplate

internal class StateFileTemplate : FileTemplate("State") {
    override fun createContent(rootPackage: String): String =
                "interface State\n" +
                        "\n" +
                        "object NoState : State\n"
}