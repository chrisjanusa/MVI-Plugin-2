package com.github.chrisjanusa.mvi.package_structure.manager.app.di

import com.github.chrisjanusa.mvi.package_structure.manager.base.Template
import com.github.chrisjanusa.mvi.package_structure.Manager

internal class KoinModuleTemplate(
    packageManager: Manager,
    fileName: String
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {
    override fun createContent(): String =
                "import ${rootPackage?.appPackage?.viewModel?.packagePath}\n" +
                "import org.koin.core.module.dsl.viewModel\n" +
                "import org.koin.dsl.module\n" +
                "\n" +
                "internal val viewModelModule = module {\n" +
                "    viewModel { parameters ->\n" +
                "        ${rootPackage?.appPackage?.viewModel?.name}(navManager = parameters.get())\n" +
                "    }\n" +
                "}"
}