package com.github.chrisjanusa.mvi.plugin.effect

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.ui.dsl.builder.Cell
import com.intellij.ui.dsl.builder.bind
import com.intellij.ui.dsl.builder.bindItem
import com.intellij.ui.dsl.builder.bindSelected
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.builder.selected
import javax.swing.JCheckBox
import javax.swing.JComponent
import javax.swing.JRadioButton

internal class EffectDialog(private val effectPromptResult: EffectPromptResult, private val actionNames: List<String>, private val navActionNames: List<String>) :
    DialogWrapper(false) {
    init {
        init()
        this.title = "Add a New Plugin"
    }

    override fun createCenterPanel(): JComponent {
        return panel {
            row("Effect Name:") {
                textField()
                    .comment("plugin_name")
                    .bindText(effectPromptResult::effectName)
                    .validationOnInput { textField ->
                        val text = textField.text
                        if (text.any { it.isLetter() && !it.isLowerCase() }) {
                            return@validationOnInput ValidationInfo("Plugin name must be lowercase", textField)
                        } else if (text.any { !it.isLetter() && it != '_' }) {
                            return@validationOnInput ValidationInfo("Plugin name must be all letters and underscores", textField)
                        } else {
                            return@validationOnInput null
                        }
                    }
                text("Effect")
            }
            lateinit var navEffectType: Cell<JRadioButton>
            buttonsGroup ("What do you need access to?") {
                row {
                    radioButton("Actions", EffectType.ACTION_ONLY)
                }
                row {
                    radioButton("Actions and State", EffectType.STATE_ACTION)
                }
                row {
                    radioButton("Actions, State, and Slice", EffectType.SLICE_STATE_ACTION)
                }
                row {
                    navEffectType = radioButton("Navigation", EffectType.NAV)
                }
            }.bind(effectPromptResult::effectType)
            lateinit var actionFilterCheckbox: Cell<JCheckBox>
            row {
                actionFilterCheckbox = checkBox("Filter for specific action")
                    .bindSelected(effectPromptResult::filterForAction)
            }
            row("Action to Filter For:") {
                comboBox(items = actionNames)
                    .bindItem(effectPromptResult::actionToFilterFor)
            }.enabledIf(actionFilterCheckbox.selected)
            row("Nav Action to Trigger:") {
                comboBox(items = navActionNames)
                    .bindItem(effectPromptResult::navAction)
            }.visibleIf(navEffectType.selected)
        }
    }
}