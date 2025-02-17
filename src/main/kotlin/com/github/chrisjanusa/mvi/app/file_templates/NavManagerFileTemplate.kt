package com.github.chrisjanusa.mvi.app.file_templates

import com.github.chrisjanusa.mvi.foundation.FileTemplate

class NavManagerFileTemplate : FileTemplate("NavManager") {
    override fun createContent(rootPackage: String): String =
                "import androidx.navigation.NavHostController\n" +
                "\n" +
                "class NavManager(private val navHostController: NavHostController) {\n" +
                "    fun navBack() {\n" +
                "        navHostController.navigateUp()\n" +
                "    }\n" +
                "}"
}