package com.github.chrisjanusa.mvi.app.file_templates

import com.github.chrisjanusa.mvi.foundation.FileTemplate
import com.intellij.openapi.actionSystem.AnActionEvent

internal class KoinModulesFileTemplate(
    actionEvent: AnActionEvent,
    rootPackage: String,
) : FileTemplate(
    actionEvent = actionEvent,
    rootPackage = rootPackage
) {
    override val fileName: String
        get() = "KoinModule"

    override fun createContent(): String =
                "import $rootPackage.app.AppViewModel\n" +
                "import org.koin.core.module.dsl.viewModel\n" +
                "import org.koin.dsl.module\n" +
                "\n" +
                "internal val appModule = module {\n" +
                "    viewModel { parameters ->\n" +
                "        AppViewModel(navManager = parameters.get())\n" +
                "    }\n" +
                "}"
}