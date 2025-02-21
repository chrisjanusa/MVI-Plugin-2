package com.github.chrisjanusa.mvi.package_generator.app.file_templates

import com.github.chrisjanusa.mvi.helper.file_creation.FileTemplate
import com.intellij.openapi.actionSystem.AnActionEvent

internal class NavEffectFileTemplate(
    actionEvent: AnActionEvent,
    rootPackage: String,
) : FileTemplate(
    actionEvent = actionEvent,
    rootPackage = rootPackage
) {
    override val fileName: String
        get() = "NavEffect"

    override fun createContent(): String =
                "import $rootPackage.app.nav.NavManager\n" +
                        "import $rootPackage.common.nav.CoreNavAction\n" +
                        "import $rootPackage.foundation.AppAction\n" +
                        "import $rootPackage.foundation.AppEffect\n" +
                        "import $rootPackage.foundation.OnAppAction\n" +
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