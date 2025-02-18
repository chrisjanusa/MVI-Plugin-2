package com.github.chrisjanusa.mvi.feature.shared

import com.github.chrisjanusa.mvi.file_managment.capitalize
import com.github.chrisjanusa.mvi.foundation.FileTemplate

class SharedEffectFileTemplate(private val featureName: String): FileTemplate("${featureName.capitalize()}SharedEffect") {
    override fun createContent(rootPackage: String): String =
                "import $rootPackage.foundation.Action\n" +
                        "import $rootPackage.$featureName.shared.generated.${featureName.capitalize()}SharedActionEffect\n" +
                        "import $rootPackage.foundation.OnAction\n" +
                        "import kotlinx.coroutines.flow.Flow\n" +
                        "import kotlinx.coroutines.flow.collectLatest\n" +
                        "\n" +
                        "internal object OnChildActionReceivedEffect : ${featureName.capitalize()}SharedActionEffect() {\n" +
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