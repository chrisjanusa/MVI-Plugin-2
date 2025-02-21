package com.github.chrisjanusa.mvi.feature.shared

import com.github.chrisjanusa.mvi.file_managment.getFeaturePackageFile
import com.github.chrisjanusa.mvi.foundation.FileTemplate
import com.intellij.openapi.actionSystem.AnActionEvent

internal class SharedStateFileTemplate(
    actionEvent: AnActionEvent,
    featurePackage: String? = actionEvent.getFeaturePackageFile()?.name
): FileTemplate(
    actionEvent = actionEvent,
    featurePackage = featurePackage
) {
    override val fileName: String
        get() = "${featureName}SharedState"

    override fun createContent(): String =
                "import $rootPackage.foundation.state.State\n" +
                        "\n" +
                        "data class ${featureName}SharedState(\n" +
                        ") : State\n"
}