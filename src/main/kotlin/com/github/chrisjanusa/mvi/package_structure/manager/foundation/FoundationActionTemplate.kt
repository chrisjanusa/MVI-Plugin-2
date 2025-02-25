package com.github.chrisjanusa.mvi.package_structure.manager.foundation

import com.github.chrisjanusa.mvi.package_structure.manager.base.Template
import com.github.chrisjanusa.mvi.package_structure.Manager

internal class FoundationActionTemplate(
    packageManager: Manager,
    fileName: String
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {

    override fun createContent(): String =
                "interface Action\n" +
                "\n" +
                "interface ReducibleAction<ActionState: State, ActionSlice: Slice>: Action {\n" +
                "    fun reduce(state: ActionState, slice: ActionSlice) : ActionState = state\n" +
                "}\n" +
                "\n" +
                "typealias OnAction = (Action) -> Unit\n" +
                "\n" +
                "interface AppAction\n" +
                "\n" +
                "interface NavAction : AppAction\n" +
                "\n" +
                "typealias OnAppAction = (AppAction) -> Unit\n"
}