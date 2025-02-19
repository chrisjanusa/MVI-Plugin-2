package com.github.chrisjanusa.mvi.plugin

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.ui.dsl.builder.bindSelected
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

internal class PluginDialog(private val pluginPromptResult: PluginPromptResult) :
    DialogWrapper(false) {
    init {
        init()
        this.title = "Initialize MVI App Module"
    }

    override fun createCenterPanel(): JComponent {
        return panel {
            row("Plugin Name:") {
                textField()
                    .comment("plugin_name")
                    .bindText(pluginPromptResult::pluginName)
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
            }
            group("What to Generate?") {
                row {
                    checkBox("Has state?")
                        .bindSelected(pluginPromptResult::createState)
                }
                row {
                    checkBox("Has slice?")
                        .bindSelected(pluginPromptResult::createSlice)
                }
                row {
                    checkBox("Is a nav destination?")
                        .bindSelected(pluginPromptResult::createNavDestination)
                }
            }
        }
    }
}