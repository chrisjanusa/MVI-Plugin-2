package com.github.chrisjanusa.mvi.helper.file_helper

fun String.pathToPackage(): String {
    return this.substringAfter("main/").substringBeforeLast(".kt").substringAfter("/").replace('/', '.')
}
