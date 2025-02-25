package com.github.chrisjanusa.mvi.package_structure.manager.common.helper

import com.github.chrisjanusa.mvi.package_structure.manager.base.Template
import com.github.chrisjanusa.mvi.package_structure.Manager

internal class ClassNameHelperTemplate(
    packageManager: Manager,
    fileName: String
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {
    override fun createContent(): String =
        "inline fun <reified T> getClassName() = T::class.simpleName ?: \"\""
}