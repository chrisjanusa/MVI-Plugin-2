package com.github.chrisjanusa.mvi.plugin.effect

internal data class EffectPromptResult(
    var effectName: String = "",
    var effectType: EffectType = EffectType.ACTION_ONLY,
    var filterForAction: Boolean = false,
    var actionToFilterFor: String? = null,
    var navAction: String? = null,
)

internal enum class EffectType {
    ACTION_ONLY,
    STATE_ACTION,
    SLICE_STATE_ACTION,
    NAV
}