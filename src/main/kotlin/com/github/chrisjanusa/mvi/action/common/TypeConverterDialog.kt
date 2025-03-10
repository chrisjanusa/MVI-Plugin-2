package com.github.chrisjanusa.mvi.action.common

import com.github.chrisjanusa.mvi.helper.ui.TextFieldDependentLabelText
import com.github.chrisjanusa.mvi.helper.ui.nameField
import com.github.chrisjanusa.mvi.helper.ui.validateNotEmpty
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

internal class TypeConverterDialog(private val typeConverterPromptResult: TypeConverterPromptResult) :
    DialogWrapper(false) {
    init {
        init()
        this.title = "Add Type Converter"
    }

    override fun createCenterPanel(): JComponent {
        return panel {
            nameField(
                type = "Type Converter",
                bindingField = typeConverterPromptResult::name,
                suffixes = listOf(
                    TextFieldDependentLabelText.PascalCaseText(
                        suffix = "TypeConverter"
                    ),
                ),
                addSeparator = false,
                initialText = typeConverterPromptResult.name
            )
            row("Type to convert:") {
                textField()
                    .bindText(typeConverterPromptResult::type)
                    .validateNotEmpty()
            }
        }
    }
}