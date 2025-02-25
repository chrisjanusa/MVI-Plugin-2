package com.github.chrisjanusa.mvi.package_structure.manager.feature.shared

import com.github.chrisjanusa.mvi.package_structure.manager.base.Template
import com.github.chrisjanusa.mvi.package_structure.Manager

internal class SharedEffectTemplate(
    packageManager: Manager,
    fileName: String
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {

    override fun createContent(): String =
                "import $rootPackagePath.foundation.Action\n" +
                        "import $rootPackagePath.$featurePackageName.shared.generated.${featureName}SharedActionEffect\n" +
                        "import $rootPackagePath.foundation.OnAction\n" +
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