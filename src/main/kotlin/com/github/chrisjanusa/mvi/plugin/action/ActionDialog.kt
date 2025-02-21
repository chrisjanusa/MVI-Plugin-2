package com.github.chrisjanusa.mvi.plugin.action

import com.github.chrisjanusa.mvi.ui.TextFieldDependentLabelText
import com.github.chrisjanusa.mvi.ui.nameField
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.dsl.builder.Cell
import com.intellij.ui.dsl.builder.bind
import com.intellij.ui.dsl.builder.bindSelected
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.builder.selected
import com.intellij.ui.layout.not
import javax.swing.JComponent
import javax.swing.JRadioButton

internal class ActionDialog(private val actionPromptResult: ActionPromptResult) :
    DialogWrapper(false) {
    init {
        init()
        this.title = "Add an Action to Plugin"
    }

    override fun createCenterPanel(): JComponent {
        return panel {
            nameField(
                type = "Action",
                bindingField = actionPromptResult::actionName,
                suffixes = listOf(
                    TextFieldDependentLabelText.PascalCaseText()
                )
            )

            lateinit var navEffectType: Cell<JRadioButton>
            buttonsGroup ("What do you need access to?") {
                row {
                    radioButton("Action without reduction", ActionType.NO_REDUCTION)
                }
                row {
                    radioButton("Reducible action", ActionType.REDUCIBLE)
                }
                row {
                    navEffectType = radioButton("Nav action", ActionType.NAV)
                }
            }.bind(actionPromptResult::actionType)
            row {
                checkBox("Has parameters?")
                    .bindSelected(actionPromptResult::hasParameters)
            }.enabledIf(navEffectType.selected.not())
        }
    }
}