package com.github.chrisjanusa.mvi.feature.shared

import com.github.chrisjanusa.mvi.file_managment.capitalize
import com.github.chrisjanusa.mvi.foundation.FileTemplate

class SharedStateFileTemplate(private val featureName: String): FileTemplate("${featureName.capitalize()}SharedState") {
    override fun createContent(rootPackage: String): String =
                "import $rootPackage.foundation.state.State\n" +
                        "\n" +
                        "data class ${featureName.capitalize()}SharedState(\n" +
                        ") : State\n"
}