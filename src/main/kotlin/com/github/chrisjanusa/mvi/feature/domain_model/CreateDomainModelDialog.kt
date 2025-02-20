package com.github.chrisjanusa.mvi.feature.domain_model

import com.github.chrisjanusa.mvi.ui.validateNotEmpty
import com.github.chrisjanusa.mvi.ui.validatePascalCase
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

internal class CreateDomainModelDialog(private val createDomainModelPromptResult: CreateDomainModelPromptResult) :
    DialogWrapper(false) {
    init {
        init()
        this.title = "Add a Domain Model to Feature"
    }

    override fun createCenterPanel(): JComponent {
        return panel {
            row("Domain Model Name:") {
                textField()
                    .comment("DomainModelName (PascalCase)")
                    .bindText(createDomainModelPromptResult::domainModelName)
                    .validatePascalCase()
                    .validateNotEmpty()
            }
        }
    }
}