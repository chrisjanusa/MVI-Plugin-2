package com.github.chrisjanusa.mvi.package_generator.app.file_templates

import com.github.chrisjanusa.mvi.helper.file_creation.FileTemplate
import com.intellij.openapi.actionSystem.AnActionEvent

internal class ApplicationFileTemplate(
    private val appName: String,
    actionEvent: AnActionEvent,
    rootPackage: String,
) : FileTemplate(
    actionEvent = actionEvent,
    rootPackage = rootPackage
) {
    override val fileName: String
        get() = "${appName}Application"

    override fun createContent(): String =
                "import android.app.Application\n" +
                        "import $rootPackage.app.di.initKoin\n" +
                        "import org.koin.android.ext.koin.androidContext\n" +
                        "\n" +
                        "class ${appName}Application: Application() {\n" +
                        "    override fun onCreate() {\n" +
                        "        super.onCreate()\n" +
                        "        initKoin {\n" +
                        "            androidContext(this@${appName}Application)\n" +
                        "        }\n" +
                        "    }\n" +
                        "}\n"
}