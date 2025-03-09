package com.github.chrisjanusa.mvi.package_structure.manager.feature.service.repository

import com.github.chrisjanusa.mvi.package_structure.Manager
import com.github.chrisjanusa.mvi.package_structure.manager.base.Template

internal class RepositoryTemplate(
    packageManager: Manager,
    fileName: String
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {

    override fun createContent(): String =
                "import ${featurePackage?.apiPackage?.packagePath}.I$fileName\n" +
                "\n" +
                "class $fileName(\n" +
                "): I$fileName {\n" +
                "}\n"
}