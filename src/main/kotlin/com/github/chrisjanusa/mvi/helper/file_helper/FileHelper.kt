package com.github.chrisjanusa.mvi.helper.file_helper

import com.intellij.openapi.vfs.VirtualFile

fun VirtualFile.isValidFile() = name != "/"
fun VirtualFile.findChildFile(name: String) = this.findChild("$name.kt")