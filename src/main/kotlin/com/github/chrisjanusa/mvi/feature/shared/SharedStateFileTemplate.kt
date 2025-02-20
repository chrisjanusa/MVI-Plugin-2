package com.github.chrisjanusa.mvi.feature.shared

import com.github.chrisjanusa.mvi.file_managment.toPascalCase
import com.github.chrisjanusa.mvi.foundation.FileTemplate

internal class SharedStateFileTemplate(private val featureName: String): FileTemplate("${featureName.toPascalCase()}SharedState") {
    override fun createContent(rootPackage: String): String =
                "import $rootPackage.foundation.state.State\n" +
                        "\n" +
                        "data class ${featureName.toPascalCase()}SharedState(\n" +
                        ") : State\n"
}