package com.github.chrisjanusa.mvi.file_managment

fun String.capitalize(): String =
    this.split("_").joinToString("") { word ->
        word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }.replaceFirstChar { it.titlecase() }