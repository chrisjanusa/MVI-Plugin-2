package com.github.chrisjanusa.mvi.app.file_templates.foundation.nav

import com.github.chrisjanusa.mvi.foundation.FileTemplate

internal class NavComponentIdFileTemplate : FileTemplate("NavComponentId") {
    override fun createContent(rootPackage: String): String =
                "internal interface NavComponentId\n"
}