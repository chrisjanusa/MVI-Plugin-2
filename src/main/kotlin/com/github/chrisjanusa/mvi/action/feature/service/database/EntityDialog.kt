package com.github.chrisjanusa.mvi.action.feature.service.database

import com.github.chrisjanusa.mvi.helper.ui.TextFieldDependentLabelText
import com.github.chrisjanusa.mvi.helper.ui.nameField
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

internal class EntityDialog(private val databasePromptResult: EntityPromptResult) :
    DialogWrapper(false) {
    init {
        init()
        this.title = "Add an Entity to Database"
    }

    override fun createCenterPanel(): JComponent {
        return panel {
            nameField(
                type = "Entity",
                bindingField = databasePromptResult::name,
                suffixes = listOf(
                    TextFieldDependentLabelText.PascalCaseText(
                        suffix = "Entity"
                    ),
                ),
                addSeparator = false,
                initialText = databasePromptResult.name,
            )
        }
    }
}