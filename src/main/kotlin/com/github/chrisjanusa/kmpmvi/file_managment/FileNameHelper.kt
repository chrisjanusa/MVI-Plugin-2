package com.github.chrisjanusa.kmpmvi.file_managment

fun String.capitalize(): String =
    this.replaceFirstChar { it.uppercase() }