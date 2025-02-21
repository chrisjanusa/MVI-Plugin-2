package com.github.chrisjanusa.mvi.feature.shared

import com.github.chrisjanusa.mvi.file_managment.getFeaturePackageFile
import com.github.chrisjanusa.mvi.foundation.FileTemplate
import com.intellij.openapi.actionSystem.AnActionEvent

internal class SharedActionFileTemplate(
    actionEvent: AnActionEvent,
    featurePackage: String? = actionEvent.getFeaturePackageFile()?.name
): FileTemplate(
    actionEvent = actionEvent,
    featurePackage = featurePackage
) {
    override val fileName: String
        get() = "${featureName}SharedAction"

    override fun createContent(): String =
                "import $rootPackage.foundation.ReducibleAction\n" +
                        "import $rootPackage.foundation.state.NoSlice\n" +
                        "\n" +
                        "internal sealed class $fileName : ReducibleAction<${featureName}SharedState, NoSlice> {\n" +
                        "}\n"
}