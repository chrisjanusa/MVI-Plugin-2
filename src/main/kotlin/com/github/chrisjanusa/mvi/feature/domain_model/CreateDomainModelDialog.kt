package com.github.chrisjanusa.mvi.feature.domain_model

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

internal class CreateDomainModelDialog(private val createDomainModelPromptResult: CreateDomainModelPromptResult) :
    DialogWrapper(false) {
    init {
        init()
        this.title = "Initialize MVI App Module"
    }

    override fun createCenterPanel(): JComponent {
        return panel {
            row("Domain Model Name:") {
                textField()
                    .comment("DomainModelName (PascalCase)")
                    .bindText(createDomainModelPromptResult::domainModelName)
                    .validationOnInput { textField ->
                        val text = textField.text
                        text.forEachIndexed { index, char ->
                            if (index == 0) {
                                if (!char.isUpperCase()) {
                                    return@validationOnInput ValidationInfo("Domain Model name must be PascalCase", textField)
                                }
                            }
                            if (!char.isLetter()) {
                                return@validationOnInput ValidationInfo("Domain Model name must be only characters", textField)
                            }
                        }
                        return@validationOnInput null
                    }
            }
        }
    }
}