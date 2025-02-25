package com.github.chrisjanusa.mvi.package_structure.manager.base

fun String.addIf(condition: () -> Boolean) = if (condition()) this else ""