package com.github.chrisjanusa.mvi.package_generator.feature.shared

import com.github.chrisjanusa.mvi.helper.file_managment.getFeaturePackageFile
import com.github.chrisjanusa.mvi.helper.file_creation.FileTemplate
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