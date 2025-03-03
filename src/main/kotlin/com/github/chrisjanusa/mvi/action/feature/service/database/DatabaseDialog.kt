package com.github.chrisjanusa.mvi.action.feature.service.database

import com.github.chrisjanusa.mvi.helper.ui.TextFieldDependentLabelText
import com.github.chrisjanusa.mvi.helper.ui.nameField
import com.github.chrisjanusa.mvi.helper.ui.stringListInputPanel
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

internal class DatabaseDialog(private val databasePromptResult: DatabasePromptResult, private val initialName: String = "") :
    DialogWrapper(false) {
    init {
        init()
        this.title = "Add a Database to Feature"
    }

    override fun createCenterPanel(): JComponent {
        return panel {
            nameField(
                type = "Database",
                bindingField = databasePromptResult::name,
                suffixes = listOf(
                    TextFieldDependentLabelText.PascalCaseText(
                        suffix = "Database"
                    ),
                    TextFieldDependentLabelText.PascalCaseText(
                        suffix = "Dao",
                    )
                ),
                addSeparator = false,
                initialText = initialName,
            )
            stringListInputPanel("Entity", databasePromptResult.entityNames, "Entity")
        }
    }
}