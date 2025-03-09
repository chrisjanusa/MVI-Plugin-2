package com.github.chrisjanusa.mvi.action.feature.shared

import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.PluginSliceFileManager

internal data class SliceUpdateEffectPromptResult(
    var slice: PluginSliceFileManager? = null,
)