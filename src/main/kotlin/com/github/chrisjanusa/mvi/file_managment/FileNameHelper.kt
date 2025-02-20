package com.github.chrisjanusa.mvi.file_managment

fun String.toPascalCase(): String =
    this.split("_").joinToString("") { word ->
        word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }.split(" ").joinToString("") { word ->
        word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }.replaceFirstChar { it.titlecase() }

fun String.toSnakeCase(): String {
    val sb = StringBuilder()
    replaceFirstChar { it.lowercaseChar() }.forEach { char ->
        if (char.isUpperCase() && char.isLetter()) {
            sb.append('_')
            sb.append(char.lowercaseChar())
        } else {
            sb.append(char)
        }
    }
    return sb.toString().replace(" ", "")
}
