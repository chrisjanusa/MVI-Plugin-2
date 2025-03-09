package com.github.chrisjanusa.mvi.package_structure.manager.feature.shared.generated

import com.github.chrisjanusa.mvi.package_structure.Manager
import com.github.chrisjanusa.mvi.package_structure.manager.base.Template

internal class SharedTypeAliasTemplate(
    packageManager: Manager,
    fileName: String
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {

    override fun createContent(): String =
        "import ${rootPackage?.foundationPackage?.effect?.packagePathExcludingFile}.ActionEffect\n" +
        "import ${rootPackage?.foundationPackage?.effect?.packagePath}\n" +
        "import ${rootPackage?.foundationPackage?.effect?.packagePathExcludingFile}.SliceUpdateEffect\n" +
        "import ${rootPackage?.foundationPackage?.effect?.packagePathExcludingFile}.StateEffect\n" +
        "import ${rootPackage?.foundationPackage?.effect?.packagePathExcludingFile}.StateSliceEffect\n" +
        "import ${featurePackage?.sharedPackage?.state?.packagePath}\n" +
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