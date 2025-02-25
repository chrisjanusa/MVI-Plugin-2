package com.github.chrisjanusa.mvi.package_structure.manager.feature.domain_model

import com.github.chrisjanusa.mvi.package_structure.manager.base.Template
import com.github.chrisjanusa.mvi.package_structure.Manager

internal class DomainModelTemplate(
    packageManager: Manager,
    fileName: String
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {

    override fun createContent(): String =
                "data class $fileName(\n" +
                        "    \n" +
                        ")\n"
}