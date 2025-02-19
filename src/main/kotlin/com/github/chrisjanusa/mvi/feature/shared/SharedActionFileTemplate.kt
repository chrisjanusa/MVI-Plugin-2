package com.github.chrisjanusa.mvi.feature.shared

import com.github.chrisjanusa.mvi.file_managment.capitalize
import com.github.chrisjanusa.mvi.foundation.FileTemplate

internal class SharedActionFileTemplate(private val featureName: String): FileTemplate("${featureName.capitalize()}SharedAction") {
    override fun createContent(rootPackage: String): String =
                "import $rootPackage.foundation.ReducibleAction\n" +
                        "import $rootPackage.foundation.state.NoSlice\n" +
                        "\n" +
                        "internal sealed class ${featureName.capitalize()}SharedAction : ReducibleAction<${featureName.capitalize()}SharedState, NoSlice> {\n" +
                        "}\n"
}