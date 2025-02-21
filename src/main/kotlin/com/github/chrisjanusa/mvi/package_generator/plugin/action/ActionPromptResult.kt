package com.github.chrisjanusa.mvi.package_generator.plugin.action

internal data class ActionPromptResult(
    var actionName: String = "",
    var actionType: ActionType = ActionType.NO_REDUCTION,
    var hasParameters: Boolean = false
)

internal enum class ActionType {
    NO_REDUCTION,
    NAV,
    REDUCIBLE,
}