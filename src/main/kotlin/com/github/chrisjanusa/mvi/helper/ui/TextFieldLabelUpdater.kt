package com.github.chrisjanusa.mvi.helper.ui

import javax.swing.JLabel
import javax.swing.JTextField
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

class TextFieldLabelUpdater(
    private val textField: JTextField,
    private val label: JLabel,
    private val textMapper: (String) -> String
) : DocumentListener {

    override fun insertUpdate(e: DocumentEvent?) {
        updateLabelText()
    }

    override fun removeUpdate(e: DocumentEvent?) {
        updateLabelText()
    }

    override fun changedUpdate(e: DocumentEvent?) {
        updateLabelText()
    }

    private fun updateLabelText() {
        label.text = textMapper(textField.text)
    }
}