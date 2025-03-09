package com.github.chrisjanusa.mvi.package_structure.manager.app.effect

import com.github.chrisjanusa.mvi.package_structure.Manager
import com.github.chrisjanusa.mvi.package_structure.manager.base.Template

internal class AppNavEffectTemplate(
    packageManager: Manager,
    fileName: String
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {

    override fun createContent(): String =
        "import ${rootPackage?.appPackage?.navManager?.packagePath}\n" +
        "import ${rootPackage?.commonPackage?.coreNavAction?.packagePath}\n" +
        "import ${rootPackage?.foundationPackage?.action?.packagePathExcludingFile}.AppAction\n" +
        "import ${rootPackage?.foundationPackage?.effect?.packagePathExcludingFile}.AppEffect\n" +
        "import ${rootPackage?.foundationPackage?.action?.packagePathExcludingFile}.OnAppAction\n" +
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