package com.github.chrisjanusa.mvi.app.file_templates

import com.github.chrisjanusa.mvi.foundation.FileTemplate

internal class KoinModulesFileTemplate : FileTemplate("KoinModule") {
    override fun createContent(rootPackage: String): String =
                "import $rootPackage.app.AppViewModel\n" +
                "import org.koin.core.module.dsl.viewModel\n" +
                "import org.koin.dsl.module\n" +
                "\n" +
                "internal val appModule = module {\n" +
                "    viewModel { parameters ->\n" +
                "        AppViewModel(navManager = parameters.get())\n" +
                "    }\n" +
                "}"
}