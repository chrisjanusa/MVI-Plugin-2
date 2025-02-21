package com.github.chrisjanusa.mvi.package_generator.feature.domain_model

import com.github.chrisjanusa.mvi.helper.file_creation.FileTemplate
import com.intellij.openapi.actionSystem.AnActionEvent

internal class DomainModelFileTemplate(
    private val domainModelName: String,
    actionEvent: AnActionEvent
): FileTemplate(actionEvent) {
    override val fileName: String
        get() = domainModelName

    override fun createContent(): String =
                "data class $fileName(\n" +
                        "    \n" +
                        ")\n"
}