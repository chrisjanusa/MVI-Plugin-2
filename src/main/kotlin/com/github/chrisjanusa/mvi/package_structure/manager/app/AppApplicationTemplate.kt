package com.github.chrisjanusa.mvi.package_structure.manager.app

import com.github.chrisjanusa.mvi.package_structure.manager.base.Template
import com.github.chrisjanusa.mvi.package_structure.Manager

internal class AppApplicationTemplate(
    packageManager: Manager,
    fileName: String
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {

    override fun createContent(): String =
                "import android.app.Application\n" +
                        "import $rootPackagePath.app.di.initKoin\n" +
                        "import org.koin.android.ext.koin.androidContext\n" +
                        "\n" +
                        "class $fileName: Application() {\n" +
                        "    override fun onCreate() {\n" +
                        "        super.onCreate()\n" +
                        "        initKoin {\n" +
                        "            androidContext(this@$fileName)\n" +
                        "        }\n" +
                        "    }\n" +
                        "}\n"
}