package com.github.chrisjanusa.mvi.package_structure.manager.app.di

import com.github.chrisjanusa.mvi.package_structure.manager.base.Template
import com.github.chrisjanusa.mvi.package_structure.Manager

internal class KoinInitTemplate(
    packageManager: Manager,
    fileName: String
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {

    override fun createContent(): String =
                "import org.koin.core.context.startKoin\n" +
                "import org.koin.dsl.KoinAppDeclaration\n" +
                "\n" +
                "fun initKoin(config: KoinAppDeclaration? = null) {\n" +
                "    startKoin {\n" +
                "        config?.invoke(this)\n" +
                "        modules(\n" +
                "            viewModelModule,\n" +
                "        )\n" +
                "    }\n" +
                "}"
}