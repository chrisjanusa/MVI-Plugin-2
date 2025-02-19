package com.github.chrisjanusa.mvi.app.file_templates.foundation

import com.github.chrisjanusa.mvi.foundation.FileTemplate

internal class SliceUpdateFileTemplate : FileTemplate("SliceUpdate") {
    override fun createContent(rootPackage: String): String =
                "interface SliceUpdate\n"
}