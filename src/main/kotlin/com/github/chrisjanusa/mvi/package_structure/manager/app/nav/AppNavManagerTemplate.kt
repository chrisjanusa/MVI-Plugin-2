package com.github.chrisjanusa.mvi.package_structure.manager.app.nav

import com.github.chrisjanusa.mvi.package_structure.manager.base.Template
import com.github.chrisjanusa.mvi.package_structure.Manager

internal class AppNavManagerTemplate(
    packageManager: Manager,
    fileName: String
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {

    override fun createContent(): String =
                "import androidx.navigation.NavHostController\n" +
                "\n" +
                "class $fileName(private val navHostController: NavHostController) {\n" +
                "    fun navBack() {\n" +
                "        navHostController.navigateUp()\n" +
                "    }\n" +
                "}"
}