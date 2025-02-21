package com.github.chrisjanusa.mvi.helper.file_managment

import com.intellij.openapi.fileTypes.FileType
import javax.swing.Icon

object KotlinFileType : FileType {
    override fun getName() = "Kotlin"

    override fun getDescription() = "Kotlin"

    override fun getDefaultExtension() = ".kt"

    override fun getIcon(): Icon? = null

    override fun isBinary() = false
}