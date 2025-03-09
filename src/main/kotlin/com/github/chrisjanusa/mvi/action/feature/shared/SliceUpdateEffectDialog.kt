package com.github.chrisjanusa.mvi.action.feature.shared

import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.PluginSliceFileManager
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.dsl.builder.bindItem
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

internal class SliceUpdateEffectDialog(
    private val sliceUpdateEffectPromptResult: SliceUpdateEffectPromptResult,
    private val slices: List<PluginSliceFileManager>,
) :
    DialogWrapper(false) {
    init {
        init()
        this.title = "Add a Slice Update to Shared State"
    }

    override fun createCenterPanel(): JComponent {
        return panel {
            row("Slice to send updates to:") {
                comboBox(items = slices)
                    .bindItem(sliceUpdateEffectPromptResult::slice)
            }
        }
    }
}