package com.github.chrisjanusa.mvi.package_generator.app.file_templates

import com.github.chrisjanusa.mvi.helper.file_creation.FileTemplate
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