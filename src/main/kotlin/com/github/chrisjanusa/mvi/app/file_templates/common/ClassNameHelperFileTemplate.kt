package com.github.chrisjanusa.mvi.app.file_templates.common

import com.github.chrisjanusa.mvi.foundation.FileTemplate

class ClassNameHelperFileTemplate : FileTemplate("ClassNameHelper") {
    override fun createContent(rootPackage: String): String =
                "inline fun <reified T> getClassName() = T::class.simpleName ?: \"\""
}