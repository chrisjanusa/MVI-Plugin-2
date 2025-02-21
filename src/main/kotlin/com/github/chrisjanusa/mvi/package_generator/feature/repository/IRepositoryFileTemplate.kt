package com.github.chrisjanusa.mvi.package_generator.feature.repository

import com.github.chrisjanusa.mvi.helper.file_creation.FileTemplate
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