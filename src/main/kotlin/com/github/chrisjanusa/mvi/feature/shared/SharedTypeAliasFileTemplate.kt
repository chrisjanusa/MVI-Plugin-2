package com.github.chrisjanusa.mvi.feature.shared

import com.github.chrisjanusa.mvi.file_managment.toPascalCase
import com.github.chrisjanusa.mvi.foundation.FileTemplate

internal class SharedTypeAliasFileTemplate(private val featureName: String): FileTemplate("${featureName.toPascalCase()}SharedTypeAlias") {
    override fun createContent(rootPackage: String): String =
                "import $rootPackage.foundation.ActionEffect\n" +
                        "import $rootPackage.foundation.Effect\n" +
                        "import $rootPackage.foundation.SliceUpdateEffect\n" +
                        "import $rootPackage.foundation.StateEffect\n" +
                        "import $rootPackage.foundation.StateSliceEffect\n" +
                        "import $rootPackage.$featureName.shared.${featureName.toPascalCase()}SharedState\n" +
                        "import $rootPackage.foundation.state.NoSlice\n" +
                        "\n" +
                        "internal typealias ${featureName.toPascalCase()}SharedEffect = Effect<${featureName.toPascalCase()}SharedState, NoSlice>\n" +
                        "\n" +
                        "internal typealias ${featureName.toPascalCase()}SharedStateEffect = StateEffect<${featureName.toPascalCase()}SharedState, NoSlice>\n" +
                        "\n" +
                        "internal typealias ${featureName.toPascalCase()}SharedStateSliceEffect = StateSliceEffect<${featureName.toPascalCase()}SharedState, NoSlice>\n" +
                        "\n" +
                        "internal typealias ${featureName.toPascalCase()}SharedSliceUpdateEffect = SliceUpdateEffect<${featureName.toPascalCase()}SharedState, NoSlice>\n" +
                        "\n" +
                        "internal typealias ${featureName.toPascalCase()}SharedActionEffect = ActionEffect<${featureName.toPascalCase()}SharedState, NoSlice>\n"
}