package com.github.chrisjanusa.mvi.helper.file_helper

fun String.toPascalCase(): String =
    this.split("_").joinToString("") { word ->
        word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }.split(" ").joinToString("") { word ->
        word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }.replaceFirstChar { it.titlecase() }

fun String.toSnakeCase(): String =
    this.split("_", " ").filterNot { it.isEmpty() }.joinToString(separator = "_") {
        val sb = StringBuilder()
        it.replaceFirstChar { firstChar -> firstChar.lowercaseChar() }.forEach { char ->
            if (char.isUpperCase() && char.isLetter()) {
                sb.append('_')
                sb.append(char.lowercaseChar())
            } else {
                sb.append(char)
            }
        }
        sb.toString()
    }

fun String.toCamelCase(): String {
    return toPascalCase().replaceFirstChar { it.lowercaseChar() }
}
