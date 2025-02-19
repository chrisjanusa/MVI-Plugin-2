package com.github.chrisjanusa.mvi.foundation

fun String.addIf(condition: () -> Boolean) = if (condition()) this else ""