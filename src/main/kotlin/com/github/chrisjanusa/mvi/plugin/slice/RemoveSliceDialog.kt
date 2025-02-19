package com.github.chrisjanusa.mvi.plugin.slice

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

internal class RemoveSliceDialog(private val pluginName: String) :
    DialogWrapper(false) {
    init {
        init()
        this.title = "Delete the Slice from $pluginName"
    }

    override fun createCenterPanel(): JComponent {
        return panel {
            row {
                text("Are you sure you want to delete the slice from $pluginName?")
            }
        }
    }
}