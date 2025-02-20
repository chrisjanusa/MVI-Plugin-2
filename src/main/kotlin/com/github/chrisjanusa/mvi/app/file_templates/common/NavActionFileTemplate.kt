package com.github.chrisjanusa.mvi.app.file_templates.common

import com.github.chrisjanusa.mvi.foundation.FileTemplate

internal class NavActionFileTemplate : FileTemplate("CoreNavAction") {
    override fun createContent(rootPackage: String): String =
                "import $rootPackage.foundation.NavAction\n" +
                "\n" +
                "sealed class CoreNavAction : NavAction {\n" +
                "    data object OnBackClick : NavAction\n" +
                "}"
}