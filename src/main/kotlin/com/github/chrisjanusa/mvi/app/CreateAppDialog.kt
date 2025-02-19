package com.github.chrisjanusa.mvi.app

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.ui.dsl.builder.*
import javax.swing.JComponent

internal class CreateAppDialog(private val createAppPromptResult: CreateAppPromptResult) :
    DialogWrapper(false) {
    init {
        init()
        this.title = "Initialize MVI App Module"
    }

    override fun createCenterPanel(): JComponent {
        return panel {
            row("App Name:") {
                textField()
                    .comment("AppName (PascalCase)")
                    .bindText(createAppPromptResult::appName)
                    .validationOnInput { textField ->
                        val text = textField.text
                        text.forEachIndexed { index, char ->
                            if (index == 0) {
                                if (!char.isUpperCase()) {
                                    return@validationOnInput ValidationInfo("Feature name must be PascalCase", textField)
                                }
                            }
                            if (!char.isLetter()) {
                                return@validationOnInput ValidationInfo("Feature name must be only characters", textField)
                            }
                        }
                        return@validationOnInput null
                    }
            }
        }
    }
}