package com.github.chrisjanusa.mvi.app.file_templates

import com.github.chrisjanusa.mvi.foundation.FileTemplate

class ApplicationFileTemplate(private val appName: String): FileTemplate("${appName}Application") {
    override fun createContent(rootPackage: String): String =
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