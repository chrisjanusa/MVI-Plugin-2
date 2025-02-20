package com.github.chrisjanusa.mvi.plugin.effect

internal data class EffectPromptResult(
    var effectName: String = "",
    var effectType: EffectType = EffectType.ACTION_ONLY,
    var filterForAction: Boolean = false,
    var actionToFilterFor: String? = noActionFilterText,
    var navAction: String? = noNavActionText,
)

const val noActionFilterText = "No Action Filter"
const val noNavActionText = "No Nav Action"

internal enum class EffectType {
    ACTION_ONLY,
    STATE_ACTION,
    SLICE_STATE_ACTION,
    NAV
}