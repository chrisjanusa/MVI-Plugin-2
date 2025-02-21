package com.github.chrisjanusa.mvi.feature.repository

import com.github.chrisjanusa.mvi.ui.TextFieldDependentLabelText
import com.github.chrisjanusa.mvi.ui.nameField
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

internal class RepositoryDialog(private val repositoryPromptResult: RepositoryPromptResult, private val initialName: String = "") :
    DialogWrapper(false) {
    init {
        init()
        this.title = "Add a Domain Model to Feature"
    }

    override fun createCenterPanel(): JComponent {
        return panel {
            nameField(
                type = "Repository",
                bindingField = repositoryPromptResult::name,
                suffixes = listOf(
                    TextFieldDependentLabelText.PascalCaseText(
                        suffix = "Repository"
                    ),
                    TextFieldDependentLabelText.PascalCaseText(
                        prefix = "I",
                        suffix = "Repository",
                    )
                ),
                addSeparator = false,
                initialText = initialName,
            )
        }
    }
}