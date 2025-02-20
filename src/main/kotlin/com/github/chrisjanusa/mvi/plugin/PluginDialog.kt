package com.github.chrisjanusa.mvi.plugin

import com.github.chrisjanusa.mvi.ui.TextFieldDependentLabelSuffix
import com.github.chrisjanusa.mvi.ui.nameField
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.dsl.builder.bindSelected
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.builder.selected
import com.intellij.ui.layout.ComponentPredicate
import javax.swing.JComponent

internal class PluginDialog(private val pluginPromptResult: PluginPromptResult) :
    DialogWrapper(false) {
    init {
        init()
        this.title = "Create a New Plugin"
    }

    override fun createCenterPanel(): JComponent {
        return panel {
            lateinit var navDestinationEnabled: ComponentPredicate
            lateinit var sliceEnabled: ComponentPredicate
            lateinit var stateEnabled: ComponentPredicate
            group("What to Generate?") {
                row {
                    stateEnabled = checkBox("Has state?")
                        .bindSelected(pluginPromptResult::createState)
                        .selected
                }
                row {
                    sliceEnabled = checkBox("Has slice?")
                        .bindSelected(pluginPromptResult::createSlice)
                        .selected
                }
                row {
                    navDestinationEnabled = checkBox("Is a nav destination?")
                        .bindSelected(pluginPromptResult::createNavDestination)
                        .selected
                }
            }
            nameField(
                type = "Plugin",
                bindingField = pluginPromptResult::pluginName,
                suffixes = listOf(
                    TextFieldDependentLabelSuffix.SnakeCaseSuffix(" - (package name)"),
                    TextFieldDependentLabelSuffix.PascalCaseSuffix("Plugin"),
                    TextFieldDependentLabelSuffix.PascalCaseSuffix("ViewModel"),
                    TextFieldDependentLabelSuffix.PascalCaseSuffix("Action"),
                    TextFieldDependentLabelSuffix.PascalCaseSuffix("Content"),
                    TextFieldDependentLabelSuffix.PascalCaseSuffix("Effect"),
                    TextFieldDependentLabelSuffix.PascalCaseSuffix("NavDestination", navDestinationEnabled),
                    TextFieldDependentLabelSuffix.PascalCaseSuffix("State", stateEnabled),
                    TextFieldDependentLabelSuffix.PascalCaseSuffix("Slice", sliceEnabled),
                )
            )
        }
    }
}