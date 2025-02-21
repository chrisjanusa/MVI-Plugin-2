package com.github.chrisjanusa.mvi.feature.shared

import com.github.chrisjanusa.mvi.file_managment.getFeaturePackageFile
import com.github.chrisjanusa.mvi.foundation.FileTemplate
import com.intellij.openapi.actionSystem.AnActionEvent

internal class SharedEffectFileTemplate(
    actionEvent: AnActionEvent,
    featurePackage: String? = actionEvent.getFeaturePackageFile()?.name
): FileTemplate(
    actionEvent = actionEvent,
    featurePackage = featurePackage
) {
    override val fileName: String
        get() = "${featureName}SharedEffect"

    override fun createContent(): String =
                "import $rootPackage.foundation.Action\n" +
                        "import $rootPackage.$featurePackage.shared.generated.${featureName}SharedActionEffect\n" +
                        "import $rootPackage.foundation.OnAction\n" +
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