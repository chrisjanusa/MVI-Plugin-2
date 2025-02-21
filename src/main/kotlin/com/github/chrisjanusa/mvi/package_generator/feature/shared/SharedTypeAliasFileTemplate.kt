package com.github.chrisjanusa.mvi.package_generator.feature.shared

import com.github.chrisjanusa.mvi.helper.file_managment.getFeaturePackageFile
import com.github.chrisjanusa.mvi.helper.file_creation.FileTemplate
import com.intellij.openapi.actionSystem.AnActionEvent

internal class SharedTypeAliasFileTemplate(
    actionEvent: AnActionEvent,
    featurePackage: String? = actionEvent.getFeaturePackageFile()?.name
): FileTemplate(
    actionEvent = actionEvent,
    featurePackage = featurePackage
) {
    override val fileName: String
        get() = "${featureName}SharedTypeAlias"

    override fun createContent(): String =
                "import $rootPackage.foundation.ActionEffect\n" +
                        "import $rootPackage.foundation.Effect\n" +
                        "import $rootPackage.foundation.SliceUpdateEffect\n" +
                        "import $rootPackage.foundation.StateEffect\n" +
                        "import $rootPackage.foundation.StateSliceEffect\n" +
                        "import $rootPackage.$featurePackage.shared.${featureName}SharedState\n" +
                        "import $rootPackage.foundation.state.NoSlice\n" +
                        "\n" +
                        "internal typealias ${featureName}SharedEffect = Effect<${featureName}SharedState, NoSlice>\n" +
                        "\n" +
                        "internal typealias ${featureName}SharedStateEffect = StateEffect<${featureName}SharedState, NoSlice>\n" +
                        "\n" +
                        "internal typealias ${featureName}SharedStateSliceEffect = StateSliceEffect<${featureName}SharedState, NoSlice>\n" +
                        "\n" +
                        "internal typealias ${featureName}SharedSliceUpdateEffect = SliceUpdateEffect<${featureName}SharedState, NoSlice>\n" +
                        "\n" +
                        "internal typealias ${featureName}SharedActionEffect = ActionEffect<${featureName}SharedState, NoSlice>\n"
}