package com.github.chrisjanusa.mvi.package_generator.app.file_templates

import com.github.chrisjanusa.mvi.helper.file_creation.FileTemplate
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