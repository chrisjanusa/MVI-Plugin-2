package com.github.chrisjanusa.mvi.package_structure.manager.feature.shared

import com.github.chrisjanusa.mvi.package_structure.Manager
import com.github.chrisjanusa.mvi.package_structure.manager.base.Template

internal class SharedEffectTemplate(
    packageManager: Manager,
    fileName: String
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {

    override fun createContent(): String =
        "import ${rootPackage?.foundationPackage?.action?.packagePath}\n" +
        "import ${featurePackage?.sharedPackage?.packagePath}.generated.${featureName}SharedActionEffect\n" +
        "import ${rootPackage?.foundationPackage?.action?.packagePathExcludingFile}.OnAction\n" +
        "import kotlinx.coroutines.flow.Flow\n" +
        "import kotlinx.coroutines.flow.collectLatest\n" +
        "\n" +
        "internal object OnChildActionReceivedEffect : ${featureName}SharedActionEffect() {\n" +
        "\n" +
        "    override suspend fun launchEffect(\n" +
        "        actionFlow: Flow<Action>,\n" +
        "        onAction: OnAction\n" +
        "    ) {\n" +
        "        actionFlow.collectLatest { action ->\n" +
        "            when (action) {\n" +
        "            }\n" +
        "        }\n" +
        "    }\n" +
        "}\n"
}