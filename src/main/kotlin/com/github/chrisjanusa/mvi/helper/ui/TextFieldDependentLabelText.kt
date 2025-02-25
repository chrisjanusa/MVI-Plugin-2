package com.github.chrisjanusa.mvi.helper.ui

import com.github.chrisjanusa.mvi.helper.file_helper.toPascalCase
import com.github.chrisjanusa.mvi.helper.file_helper.toSnakeCase
import com.intellij.ui.layout.ComponentPredicate

sealed class TextFieldDependentLabelText(
    open val prefix: String = "",
    open val suffix: String = "",
    open val visibleIf: ComponentPredicate?,
    open val textMapper: (String) -> String
) {
    data class SnakeCaseText(
        override val prefix: String = "",
        override val suffix: String = "",
        override val visibleIf: ComponentPredicate? = null,
    ) : TextFieldDependentLabelText(prefix, suffix, visibleIf, { it.toSnakeCase() })

    data class PascalCaseText(
        override val prefix: String = "",
        override val suffix: String = "",
        override val visibleIf: ComponentPredicate? = null,
    ) : TextFieldDependentLabelText(prefix, suffix, visibleIf, { it.toPascalCase() })

    data class CustomMapperText(
        override val prefix: String = "",
        override val suffix: String = "",
        override val textMapper: (String) -> String,
        override val visibleIf: ComponentPredicate? = null,
    ) : TextFieldDependentLabelText(prefix, suffix, visibleIf, textMapper)
}