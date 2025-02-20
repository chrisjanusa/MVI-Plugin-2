package com.github.chrisjanusa.mvi.feature.shared

import com.github.chrisjanusa.mvi.file_managment.toPascalCase
import com.github.chrisjanusa.mvi.foundation.FileTemplate

internal class SharedEffectFileTemplate(private val featureName: String): FileTemplate("${featureName.toPascalCase()}SharedEffect") {
    override fun createContent(rootPackage: String): String =
                "import $rootPackage.foundation.Action\n" +
                        "import $rootPackage.$featureName.shared.generated.${featureName.toPascalCase()}SharedActionEffect\n" +
                        "import $rootPackage.foundation.OnAction\n" +
                        "import kotlinx.coroutines.flow.Flow\n" +
                        "import kotlinx.coroutines.flow.collectLatest\n" +
                        "\n" +
                        "internal object OnChildActionReceivedEffect : ${featureName.toPascalCase()}SharedActionEffect() {\n" +
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