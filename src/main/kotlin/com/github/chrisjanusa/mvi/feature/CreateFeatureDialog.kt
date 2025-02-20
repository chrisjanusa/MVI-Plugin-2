package com.github.chrisjanusa.mvi.feature

import com.github.chrisjanusa.mvi.ui.TextFieldDependentLabelSuffix
import com.github.chrisjanusa.mvi.ui.nameField
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.dsl.builder.bindSelected
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.builder.selected
import com.intellij.ui.layout.ComponentPredicate
import javax.swing.JComponent

internal class CreateFeatureDialog(private val createFeaturePromptResult: CreateFeaturePromptResult) :
    DialogWrapper(false) {
    init {
        init()
        this.title = "Create a New Feature Module"
    }

    override fun createCenterPanel(): JComponent {
        return panel {
            lateinit var sharedEnabled: ComponentPredicate
            lateinit var navGraphEnabled: ComponentPredicate
            group("What to Generate?") {
                row {
                    sharedEnabled = checkBox("Generate shared state")
                        .bindSelected(createFeaturePromptResult::createSharedState)
                        .selected
                }
                row {
                    navGraphEnabled = checkBox("Generate nav graph")
                        .bindSelected(createFeaturePromptResult::createNavGraph)
                        .selected
                }
                row {
                    checkBox("Generate plugin module").selected(true).enabled(false)
                }
            }
            nameField(
                type = "Feature",
                bindingField = createFeaturePromptResult::featureName,
                suffixes = listOf(
                    TextFieldDependentLabelSuffix.SnakeCaseSuffix(" - (package name)"),
                    TextFieldDependentLabelSuffix.SnakeCaseSuffix("/plugin - (package name)"),
                    TextFieldDependentLabelSuffix.SnakeCaseSuffix("/nav - (package name)", navGraphEnabled),
                    TextFieldDependentLabelSuffix.PascalCaseSuffix("NavGraph", navGraphEnabled),
                    TextFieldDependentLabelSuffix.SnakeCaseSuffix("/shared - (package name)", sharedEnabled),
                    TextFieldDependentLabelSuffix.PascalCaseSuffix("SharedState", sharedEnabled),
                    TextFieldDependentLabelSuffix.PascalCaseSuffix("SharedViewModel", sharedEnabled),
                    TextFieldDependentLabelSuffix.PascalCaseSuffix("SharedAction", sharedEnabled),
                    TextFieldDependentLabelSuffix.PascalCaseSuffix("SharedEffect", sharedEnabled),
                )
            )
        }
    }
}