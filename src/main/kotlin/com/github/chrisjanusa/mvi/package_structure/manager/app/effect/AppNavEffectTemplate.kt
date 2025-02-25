package com.github.chrisjanusa.mvi.package_structure.manager.app.effect

import com.github.chrisjanusa.mvi.package_structure.manager.base.Template
import com.github.chrisjanusa.mvi.package_structure.Manager

internal class AppNavEffectTemplate(
    packageManager: Manager,
    fileName: String
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {

    override fun createContent(): String =
                "import $rootPackagePath.app.nav.NavManager\n" +
                        "import $rootPackagePath.common.nav.CoreNavAction\n" +
                        "import $rootPackagePath.foundation.AppAction\n" +
                        "import $rootPackagePath.foundation.AppEffect\n" +
                        "import $rootPackagePath.foundation.OnAppAction\n" +
                        "import kotlinx.coroutines.flow.Flow\n" +
                        "import kotlinx.coroutines.flow.collectLatest\n" +
                        "import kotlinx.coroutines.flow.filterIsInstance\n" +
                        "\n" +
                        "internal object OnBackClickEffect : AppEffect {\n" +
                        "    override suspend fun launchEffect(actionFlow: Flow<AppAction>, navManager: NavManager, onAction: OnAppAction) {\n" +
                        "        actionFlow.filterIsInstance<CoreNavAction.OnBackClick>().collectLatest {\n" +
                        "            navManager.navBack()\n" +
                        "        }\n" +
                        "    }\n" +
                        "}\n"
}