package com.github.chrisjanusa.kmpmvi.feature

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.ui.dsl.builder.*
import javax.swing.JCheckBox
import javax.swing.JComponent

internal class CreateFeatureDialog(private val createFeaturePromptResult: CreateFeaturePromptResult) :
    DialogWrapper(false) {
    init {
        init()
        this.title = "Create a New Feature Module"
    }
    var navGraphCheckbox: Cell<JCheckBox>? = null

    override fun createCenterPanel(): JComponent {
        return panel {
            row("Feature Name:") {
                textField()
                    .comment("feature_name")
                    .bindText(createFeaturePromptResult::featureName)
                    .validationOnInput { textField ->
                        val text = textField.text
                        if (text.any { it.isLetter() && !it.isLowerCase() }) {
                            return@validationOnInput ValidationInfo("Feature name must be lowercase", textField)
                        } else if (text.any { !it.isLetter() && it != '_' }) {
                            return@validationOnInput ValidationInfo("Feature name must be all letters and underscores", textField)
                        } else {
                            return@validationOnInput null
                        }
                    }
            }
            group("What to Generate?") {
                row {
                    checkBox("Generate service module")
                        .bindSelected(createFeaturePromptResult::createServiceModule)
                }
                row {
                    checkBox("Generate domain module")
                        .bindSelected(createFeaturePromptResult::createDomainModelModule)
                }
                row {
                    checkBox("Generate shared viewmodel")
                        .bindSelected(createFeaturePromptResult::createSharedViewModel)
                        .onIsModified {
                            if (createFeaturePromptResult.createSharedViewModel) {
                                navGraphCheckbox?.selected(true)
                                navGraphCheckbox?.enabled(false)
                            } else {
                                navGraphCheckbox?.enabled(true)
                            }
                            return@onIsModified true
                        }
                }
                row {
                    navGraphCheckbox = checkBox("Generate nav graph")
                        .bindSelected(createFeaturePromptResult::createNavGraph)
                }
                row {
                    checkBox("Generate plugin module").selected(true).enabled(false)
                }
            }
        }
    }
}