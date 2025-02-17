package com.github.chrisjanusa.mvi.app.file_templates.foundation.state

import com.github.chrisjanusa.mvi.foundation.FileTemplate

class SliceFileTemplate : FileTemplate("Slice") {
    override fun createContent(rootPackage: String): String =
                "interface Slice\n" +
                        "\n" +
                        "object NoSlice : Slice\n"
}