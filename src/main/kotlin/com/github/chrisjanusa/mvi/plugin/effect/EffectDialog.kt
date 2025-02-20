package com.github.chrisjanusa.mvi.plugin.effect

import com.github.chrisjanusa.mvi.ui.TextFieldDependentLabelSuffix
import com.github.chrisjanusa.mvi.ui.nameField
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.dsl.builder.Cell
import com.intellij.ui.dsl.builder.bind
import com.intellij.ui.dsl.builder.bindItem
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.builder.selected
import javax.swing.JComponent
import javax.swing.JRadioButton

internal class EffectDialog(
    private val effectPromptResult: EffectPromptResult,
    private val actionNames: List<String>,
    private val navActionNames: List<String>,
    private val pluginHasSlice: Boolean
) :
    DialogWrapper(false) {
    init {
        init()
        this.title = "Add an Effect to Plugin"
    }

    override fun createCenterPanel(): JComponent {
        return panel {
            nameField(
                type = "Effect",
                bindingField = effectPromptResult::effectName,
                suffixes = listOf(
                    TextFieldDependentLabelSuffix.PascalCaseSuffix("Effect")
                )
            )
            lateinit var navEffectType: Cell<JRadioButton>
            buttonsGroup("What do you need access to?") {
                row {
                    radioButton("Actions", EffectType.ACTION_ONLY)
                }
                row {
                    radioButton("Actions and State", EffectType.STATE_ACTION)
                }
                row {
                    radioButton("Actions, State, and Slice", EffectType.SLICE_STATE_ACTION).enabled(pluginHasSlice)
                }
                row {
                    navEffectType = radioButton("Navigation", EffectType.NAV)
                }
            }.bind(effectPromptResult::effectType)
            row("Action to Filter For:") {
                comboBox(items = listOf(noActionFilterText) + actionNames)
                    .bindItem(effectPromptResult::actionToFilterFor)
            }
            row("Nav Action to Trigger:") {
                comboBox(items = listOf(noNavActionText) + navActionNames)
                    .bindItem(effectPromptResult::navAction)
            }.visibleIf(navEffectType.selected)
        }
    }
}