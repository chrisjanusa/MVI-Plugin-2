package com.github.chrisjanusa.mvi.feature.domain_model

import com.github.chrisjanusa.mvi.foundation.FileTemplate
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