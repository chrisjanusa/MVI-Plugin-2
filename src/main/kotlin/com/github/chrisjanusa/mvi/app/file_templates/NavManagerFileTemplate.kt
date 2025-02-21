package com.github.chrisjanusa.mvi.app.file_templates

import com.github.chrisjanusa.mvi.foundation.FileTemplate
import com.intellij.openapi.actionSystem.AnActionEvent

internal class NavManagerFileTemplate(
    actionEvent: AnActionEvent,
    rootPackage: String,
) : FileTemplate(
    actionEvent = actionEvent,
    rootPackage = rootPackage
) {
    override val fileName: String
        get() = "NavManager"

    override fun createContent(): String =
                "import androidx.navigation.NavHostController\n" +
                "\n" +
                "class NavManager(private val navHostController: NavHostController) {\n" +
                "    fun navBack() {\n" +
                "        navHostController.navigateUp()\n" +
                "    }\n" +
                "}"
}