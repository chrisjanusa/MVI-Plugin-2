package com.github.chrisjanusa.mvi.package_structure.manager.common.service.data

import com.github.chrisjanusa.mvi.package_structure.Manager
import com.github.chrisjanusa.mvi.package_structure.manager.base.Template

internal class CommonServiceErrorTemplate(
    packageManager: Manager,
    fileName: String,
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {
    override fun createContent(): String =
        "interface Error\n"
}