package com.github.chrisjanusa.mvi.ui

import com.intellij.ui.dsl.builder.Cell
import com.intellij.ui.dsl.builder.Panel
import com.intellij.ui.dsl.builder.bindText
import javax.swing.JTextField
import kotlin.reflect.KMutableProperty0

fun Panel.nameField(
    type: String,
    bindingField: KMutableProperty0<String>,
    suffixes: List<TextFieldDependentLabelText>,
    addSeparator: Boolean = true,
    initialText: String = "",
) {
    if (addSeparator) {
        separator()
    }
    lateinit var nameField: Cell<JTextField>
    row("$type Name:") {
        nameField = textField()
            .bindText(bindingField)
            .validateChars()
            .validateNotEmpty()
    }
    row {
        label("Generated classes:")
    }
    suffixes.forEach {
        getTextFieldDependentLabelBuilder(nameField, initialText)
            .addBullet()
            .setTextMapper(it.textMapper)
            .addSuffix(it.suffix)
            .setVisibilityModifier(it.visibleIf)
            .build()
    }
}