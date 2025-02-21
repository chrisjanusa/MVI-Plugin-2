package com.github.chrisjanusa.mvi.app.file_templates

import com.github.chrisjanusa.mvi.foundation.FileTemplate
import com.intellij.openapi.actionSystem.AnActionEvent

internal class InitKoinFileTemplate(
    actionEvent: AnActionEvent,
    rootPackage: String,
) : FileTemplate(
    actionEvent = actionEvent,
    rootPackage = rootPackage
) {
    override val fileName: String
        get() = "InitKoin"

    override fun createContent(): String =
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