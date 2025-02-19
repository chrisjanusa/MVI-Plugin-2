package com.github.chrisjanusa.mvi.feature.shared

import com.github.chrisjanusa.mvi.file_managment.capitalize
import com.github.chrisjanusa.mvi.foundation.FileTemplate

internal class SharedTypeAliasFileTemplate(private val featureName: String): FileTemplate("${featureName.capitalize()}SharedTypeAlias") {
    override fun createContent(rootPackage: String): String =
                "import $rootPackage.foundation.ActionEffect\n" +
                        "import $rootPackage.foundation.Effect\n" +
                        "import $rootPackage.foundation.SliceUpdateEffect\n" +
                        "import $rootPackage.foundation.StateEffect\n" +
                        "import $rootPackage.foundation.StateSliceEffect\n" +
                        "import $rootPackage.$featureName.shared.${featureName.capitalize()}SharedState\n" +
                        "import $rootPackage.foundation.state.NoSlice\n" +
                        "\n" +
                        "internal typealias ${featureName.capitalize()}SharedEffect = Effect<${featureName.capitalize()}SharedState, NoSlice>\n" +
                        "\n" +
                        "internal typealias ${featureName.capitalize()}SharedStateEffect = StateEffect<${featureName.capitalize()}SharedState, NoSlice>\n" +
                        "\n" +
                        "internal typealias ${featureName.capitalize()}SharedStateSliceEffect = StateSliceEffect<${featureName.capitalize()}SharedState, NoSlice>\n" +
                        "\n" +
                        "internal typealias ${featureName.capitalize()}SharedSliceUpdateEffect = SliceUpdateEffect<${featureName.capitalize()}SharedState, NoSlice>\n" +
                        "\n" +
                        "internal typealias ${featureName.capitalize()}SharedActionEffect = ActionEffect<${featureName.capitalize()}SharedState, NoSlice>\n"
}