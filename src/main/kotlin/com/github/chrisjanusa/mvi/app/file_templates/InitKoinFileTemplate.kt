package com.github.chrisjanusa.mvi.app.file_templates

import com.github.chrisjanusa.mvi.foundation.FileTemplate

internal class InitKoinFileTemplate : FileTemplate("InitKoin") {
    override fun createContent(rootPackage: String): String =
                "import org.koin.core.context.startKoin\n" +
                "import org.koin.dsl.KoinAppDeclaration\n" +
                "\n" +
                "fun initKoin(config: KoinAppDeclaration? = null) {\n" +
                "    startKoin {\n" +
                "        config?.invoke(this)\n" +
                "        modules(appModule)\n" +
                "    }\n" +
                "}"
}