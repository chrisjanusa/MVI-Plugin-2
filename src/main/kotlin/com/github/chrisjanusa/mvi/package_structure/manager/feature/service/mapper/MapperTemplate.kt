package com.github.chrisjanusa.mvi.package_structure.manager.feature.service.mapper

import com.github.chrisjanusa.mvi.package_structure.Manager
import com.github.chrisjanusa.mvi.package_structure.manager.base.Template

internal class MapperTemplate(
    packageManager: Manager,
    fileName: String,
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {

    override fun createContent(): String {
        return ""
    }
}