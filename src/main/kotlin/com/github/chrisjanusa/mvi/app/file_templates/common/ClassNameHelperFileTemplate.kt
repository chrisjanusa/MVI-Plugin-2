package com.github.chrisjanusa.mvi.app.file_templates.common

import com.github.chrisjanusa.mvi.foundation.FileTemplate
import com.intellij.openapi.actionSystem.AnActionEvent

internal class ClassNameHelperFileTemplate(
    actionEvent: AnActionEvent,
    rootPackage: String
) : FileTemplate(
    actionEvent = actionEvent,
    rootPackage = rootPackage
) {
    override val fileName: String
        get() = "ClassNameHelper"

    override fun createContent(): String =
                "inline fun <reified T> getClassName() = T::class.simpleName ?: \"\""
}