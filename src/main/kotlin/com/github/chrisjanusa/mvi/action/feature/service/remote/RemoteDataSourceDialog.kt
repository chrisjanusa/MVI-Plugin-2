package com.github.chrisjanusa.mvi.action.feature.service.remote

import com.github.chrisjanusa.mvi.helper.ui.TextFieldDependentLabelText
import com.github.chrisjanusa.mvi.helper.ui.TextFieldLabelUpdater
import com.github.chrisjanusa.mvi.helper.ui.nameField
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JTextField

internal class RemoteDataSourceDialog(private val remotePromptResult: RemoteDataSourcePromptResult, private val initialName: String = "") :
    DialogWrapper(false) {
    init {
        init()
        this.title = "Add a Database to Feature"
    }

    override fun createCenterPanel(): JComponent {
        return panel {
            nameField(
                type = "Remote Data Source",
                bindingField = remotePromptResult::name,
                suffixes = listOf(
                    TextFieldDependentLabelText.PascalCaseText(
                        prefix = "Remote",
                        suffix = "DataSource"
                    ),
                    TextFieldDependentLabelText.PascalCaseText(
                        suffix = "ResponseDTO",
                    )
                ),
                addSeparator = false,
                initialText = initialName,
            )
            lateinit var baseUrlField: JTextField
            lateinit var endpointField: JTextField
            row("Base URL") {
                baseUrlField = textField()
                    .bindText(remotePromptResult::baseUrl)
                    .component
            }
            row("Endpoint") {
                endpointField = textField()
                    .bindText(remotePromptResult::endpoint)
                    .component
            }
            lateinit var label: JLabel
            row("Full URL") {
                label = label("").component
            }
            baseUrlField.document.addDocumentListener(
                TextFieldLabelUpdater(
                    baseUrlField,
                    label
                ) { text ->
                    "$text${endpointField.text}"
                }
            )
            endpointField.document.addDocumentListener(
                TextFieldLabelUpdater(
                    endpointField,
                    label
                ) { text ->
                    "${baseUrlField.text}$text"
                }
            )
        }
    }
}