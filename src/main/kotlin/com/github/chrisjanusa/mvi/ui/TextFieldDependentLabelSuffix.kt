package com.github.chrisjanusa.mvi.ui

import com.github.chrisjanusa.mvi.file_managment.toPascalCase
import com.github.chrisjanusa.mvi.file_managment.toSnakeCase
import com.intellij.ui.layout.ComponentPredicate

sealed class TextFieldDependentLabelSuffix(open val suffix: String, open val visibleIf: ComponentPredicate?, open val textMapper: (String) -> String) {
    data class SnakeCaseSuffix(
        override val suffix: String,
        override val visibleIf: ComponentPredicate? = null,
    ) : TextFieldDependentLabelSuffix(suffix, visibleIf, { it.toSnakeCase() })

    data class PascalCaseSuffix(
        override val suffix: String,
        override val visibleIf: ComponentPredicate? = null,
    ) : TextFieldDependentLabelSuffix(suffix, visibleIf, { it.toPascalCase() })

    data class CustomMapperSuffix(
        override val suffix: String,
        override val textMapper: (String) -> String,
        override val visibleIf: ComponentPredicate? = null,
    ) : TextFieldDependentLabelSuffix(suffix, visibleIf, textMapper)
}