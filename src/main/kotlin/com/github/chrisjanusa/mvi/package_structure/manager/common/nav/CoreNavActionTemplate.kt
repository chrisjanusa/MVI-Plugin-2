package com.github.chrisjanusa.mvi.package_structure.manager.common.nav

import com.github.chrisjanusa.mvi.package_structure.Manager
import com.github.chrisjanusa.mvi.package_structure.manager.base.Template

internal class CoreNavActionTemplate(
    packageManager: Manager,
    fileName: String
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {
    override fun createContent(): String =
        "import ${foundationPackage?.packagePath}.NavAction\n" +
        "\n" +
        "sealed class $fileName : NavAction {\n" +
        "    data object OnBackClick : NavAction\n" +
        "}"
}