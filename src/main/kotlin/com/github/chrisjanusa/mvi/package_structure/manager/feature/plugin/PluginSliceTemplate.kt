package com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin

import com.github.chrisjanusa.mvi.package_structure.manager.base.Template
import com.github.chrisjanusa.mvi.package_structure.Manager

internal class PluginSliceTemplate(
    packageManager: Manager,
    fileName: String
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {

    override fun createContent(): String =
        "import ${foundationPackage?.slice?.packagePath}\n" +
        "\n" +
        "data class $fileName(\n" +
        ") : ${foundationPackage?.slice?.name}\n"
}