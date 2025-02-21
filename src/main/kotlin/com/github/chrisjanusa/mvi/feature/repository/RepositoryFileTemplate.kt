package com.github.chrisjanusa.mvi.feature.repository

import com.github.chrisjanusa.mvi.foundation.FileTemplate
import com.intellij.openapi.actionSystem.AnActionEvent

internal class RepositoryFileTemplate(
    private val name: String,
    actionEvent: AnActionEvent
): FileTemplate(actionEvent) {
    override val fileName: String
        get() = "${name}Repository"

    override fun createContent(): String =
                "import $rootPackage.$featurePackage.api.I$fileName\n" +
                "\n" +
                "class $fileName(\n" +
                "): I$fileName {\n" +
                "}\n"
}