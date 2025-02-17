package com.github.chrisjanusa.mvi.app.file_templates.foundation

import com.github.chrisjanusa.mvi.foundation.FileTemplate

class ActionFileTemplate : FileTemplate("Action") {
    override fun createContent(rootPackage: String): String =
                "import $rootPackage.foundation.state.BaseSlice\n" +
                "import $rootPackage.foundation.state.BaseState\n" +
                "\n" +
                "interface Action\n" +
                "\n" +
                "interface ReducibleAction<State: BaseState, Slice: BaseSlice>: Action {\n" +
                "    fun reduce(state: State, slice: Slice) : State = state\n" +
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