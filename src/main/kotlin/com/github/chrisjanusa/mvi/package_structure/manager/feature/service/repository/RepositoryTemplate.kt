package com.github.chrisjanusa.mvi.package_structure.manager.feature.service.repository

import com.github.chrisjanusa.mvi.package_structure.manager.base.Template
import com.github.chrisjanusa.mvi.package_structure.Manager

internal class RepositoryTemplate(
    packageManager: Manager,
    fileName: String
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {

    override fun createContent(): String =
                "import $rootPackagePath.$featurePackageName.api.I$fileName\n" +
                "\n" +
                "class $fileName(\n" +
                "): I$fileName {\n" +
                "}\n"
}