package com.github.chrisjanusa.mvi.action.feature

import com.github.chrisjanusa.mvi.helper.ui.TextFieldDependentLabelText
import com.github.chrisjanusa.mvi.helper.ui.nameField
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
                    TextFieldDependentLabelText.SnakeCaseText(suffix = " - (package name)"),
                    TextFieldDependentLabelText.SnakeCaseText(suffix = "/plugin - (package name)"),
                    TextFieldDependentLabelText.SnakeCaseText(suffix = "/nav - (package name)", visibleIf = navGraphEnabled),
                    TextFieldDependentLabelText.PascalCaseText(suffix = "NavGraph", visibleIf = navGraphEnabled),
                    TextFieldDependentLabelText.SnakeCaseText(suffix = "/shared - (package name)", visibleIf = sharedEnabled),
                    TextFieldDependentLabelText.PascalCaseText(suffix = "SharedState", visibleIf = sharedEnabled),
                    TextFieldDependentLabelText.PascalCaseText(suffix = "SharedViewModel", visibleIf = sharedEnabled),
                    TextFieldDependentLabelText.PascalCaseText(suffix = "SharedAction", visibleIf = sharedEnabled),
                    TextFieldDependentLabelText.PascalCaseText(suffix = "SharedEffect", visibleIf = sharedEnabled),
                )
            )
        }
    }
}