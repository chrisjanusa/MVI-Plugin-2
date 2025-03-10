package com.github.chrisjanusa.mvi.action.app

import com.github.chrisjanusa.mvi.helper.ui.TextFieldDependentLabelText
import com.github.chrisjanusa.mvi.helper.ui.nameField
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

internal class CreateAppDialog(private val createAppPromptResult: CreateAppPromptResult) :
    DialogWrapper(false) {
    init {
        init()
        this.title = "Initialize MVI App Module"
    }

    override fun createCenterPanel(): JComponent {
        return panel {
            nameField(
                type = "App",
                bindingField = createAppPromptResult::appName,
                suffixes = listOf(
                    TextFieldDependentLabelText.PascalCaseText(
                        suffix = "Application"
                    ),
                    TextFieldDependentLabelText.PascalCaseText(
                        suffix = "Activity"
                    ),
                    TextFieldDependentLabelText.PascalCaseText(
                        suffix = "ViewModel"
                    )
                ),
                addSeparator = false,
                initialText = createAppPromptResult.appName
            )
            row {
                label("    • InitKoin")
            }
            row {
                label("    • KoinModule")
            }
            row {
                label("    • NavEffect")
            }
            row {
                label("    • NavManager")
            }
        }
    }
}