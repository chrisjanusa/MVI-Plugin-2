package com.github.chrisjanusa.mvi.ui

import com.github.chrisjanusa.mvi.file_managment.toPascalCase
import com.github.chrisjanusa.mvi.file_managment.toSnakeCase
import com.intellij.ui.dsl.builder.Cell
import com.intellij.ui.dsl.builder.Panel
import com.intellij.ui.layout.ComponentPredicate
import javax.swing.JLabel
import javax.swing.JTextField

class TextFieldDependentLabelBuilder(
    private val panel: Panel,
    private val textField: JTextField,
    private val initialText: String = ""
) {
    private var prefix = ""
    private var suffix = ""
    private var textMapper: (String) -> String = { it }
    private var visibleIf: ComponentPredicate? = null

    fun addBullet(): TextFieldDependentLabelBuilder {
        prefix += "    • "
        return this
    }

    fun addPrefix(prefix: String): TextFieldDependentLabelBuilder {
        this.prefix += prefix
        return this
    }

    fun addSuffix(suffix: String): TextFieldDependentLabelBuilder {
        this.suffix += suffix
        return this
    }

    fun setTextMapper(textMapper: (String) -> String): TextFieldDependentLabelBuilder {
        this.textMapper = textMapper
        return this
    }

    fun setPascalCaseMapper(): TextFieldDependentLabelBuilder {
        textMapper = {
            it.toPascalCase()
        }
        return this
    }

    fun setSnakeCaseMapper(): TextFieldDependentLabelBuilder {
        textMapper = {
            it.toSnakeCase()
        }
        return this
    }

    fun setVisibilityModifier(visibleIf: ComponentPredicate?): TextFieldDependentLabelBuilder {
        this.visibleIf = visibleIf
        return this
    }

    fun build(): Cell<JLabel> {
        lateinit var label: Cell<JLabel>
        panel.run {
            val row = row {
                label = label("$prefix${textMapper(initialText)}$suffix")
            }
            visibleIf?.let { row.visibleIf(it) }
        }
        textField.document.addDocumentListener(
            TextFieldLabelUpdater(
                textField,
                label.component
            ) { text ->
                "$prefix${textMapper(text)}$suffix"
            }
        )
        return label
    }
}

fun Panel.getTextFieldDependentLabelBuilder(textField: Cell<JTextField>, initialText: String = "") =
    TextFieldDependentLabelBuilder(this, textField.component, initialText)