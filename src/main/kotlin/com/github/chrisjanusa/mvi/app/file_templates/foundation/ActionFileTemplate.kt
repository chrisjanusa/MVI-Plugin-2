package com.github.chrisjanusa.mvi.app.file_templates.foundation

import com.github.chrisjanusa.mvi.foundation.FileTemplate

class ActionFileTemplate : FileTemplate("Action") {
    override fun createContent(rootPackage: String): String =
                "import $rootPackage.foundation.state.Slice\n" +
                "import $rootPackage.foundation.state.State\n" +
                "\n" +
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