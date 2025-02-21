package com.github.chrisjanusa.mvi.helper.file_creation

fun String.addIf(condition: () -> Boolean) = if (condition()) this else ""