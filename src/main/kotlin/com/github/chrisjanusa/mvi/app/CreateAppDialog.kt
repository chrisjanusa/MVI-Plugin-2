package com.github.chrisjanusa.mvi.app

import com.github.chrisjanusa.mvi.ui.TextFieldDependentLabelText
import com.github.chrisjanusa.mvi.ui.nameField
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
                type = "App Model",
                bindingField = createAppPromptResult::appName,
                suffixes = listOf(
                    TextFieldDependentLabelText.PascalCaseText(
                        suffix = "Application"
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
                label("    • MainActivity")
            }
            row {
                label("    • ActivityViewModel")
            }
            row {
                label("    • NavManager")
            }
        }
    }
}