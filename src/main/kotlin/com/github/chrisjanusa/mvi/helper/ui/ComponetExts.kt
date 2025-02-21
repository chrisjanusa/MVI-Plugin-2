package com.github.chrisjanusa.mvi.helper.ui

import com.intellij.openapi.ui.ValidationInfo
import com.intellij.ui.components.JBTextField
import com.intellij.ui.dsl.builder.Cell

fun Cell<JBTextField>.validateSnakeCase() =  validationOnInput { textField ->
    val text = textField.text
    if (text.any { it.isLetter() && !it.isLowerCase() }) {
        return@validationOnInput ValidationInfo("Name must be lowercase", textField)
    } else if (text.any { !it.isLetter() && it != '_' }) {
        return@validationOnInput ValidationInfo("Name must be all letters and underscores", textField)
    } else {
        return@validationOnInput null
    }
}

fun Cell<JBTextField>.validatePascalCase() =  validationOnInput { textField ->
    val text = textField.text
    text.forEachIndexed { index, char ->
        if (index == 0) {
            if (!char.isUpperCase()) {
                return@validationOnInput ValidationInfo("Name must be PascalCase", textField)
            }
        }
        if (!char.isLetter()) {
            return@validationOnInput ValidationInfo("Name must be only characters", textField)
        }
    }
    return@validationOnInput null
}

fun Cell<JBTextField>.validateChars() =  validationOnInput { textField ->
    val text = textField.text
    text.forEach { char ->
        if (!char.isLetter() && char != '_') {
            return@validationOnInput ValidationInfo("Name must be only characters and underscores", textField)
        }
    }
    return@validationOnInput null
}

fun Cell<JBTextField>.validateNotEmpty() =  validationOnApply { textField ->
    val text = textField.text
    if (text.isBlank()) {
        return@validationOnApply ValidationInfo("Name must not be blank", textField)
    }
    return@validationOnApply null
}