package com.github.chrisjanusa.mvi.feature.repository

import com.github.chrisjanusa.mvi.foundation.FileTemplate
import com.intellij.openapi.actionSystem.AnActionEvent

internal class IRepositoryFileTemplate(
    private val name: String,
    actionEvent: AnActionEvent
): FileTemplate(actionEvent) {
    override val fileName: String
        get() = "I${name}Repository"

    override fun createContent(): String =
                "interface $fileName {\n" +
                "}\n"
}