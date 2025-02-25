package com.github.chrisjanusa.mvi.package_structure.manager.feature.shared.generated

import com.github.chrisjanusa.mvi.package_structure.manager.base.Template
import com.github.chrisjanusa.mvi.package_structure.Manager

internal class SharedTypeAliasTemplate(
    packageManager: Manager,
    fileName: String
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {

    override fun createContent(): String =
                "import $rootPackagePath.foundation.ActionEffect\n" +
                        "import $rootPackagePath.foundation.Effect\n" +
                        "import $rootPackagePath.foundation.SliceUpdateEffect\n" +
                        "import $rootPackagePath.foundation.StateEffect\n" +
                        "import $rootPackagePath.foundation.StateSliceEffect\n" +
                        "import $rootPackagePath.$featurePackageName.shared.${featureName}SharedState\n" +
                        "import ${foundationPackage?.slice?.packagePathExcludingFile}.NoSlice\n" +
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