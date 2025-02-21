package com.github.chrisjanusa.mvi.package_generator.app.file_templates.foundation

import com.github.chrisjanusa.mvi.helper.file_creation.FileTemplate
import com.intellij.openapi.actionSystem.AnActionEvent

internal class ActionFileTemplate(
    actionEvent: AnActionEvent,
    rootPackage: String
) : FileTemplate(
    actionEvent = actionEvent,
    rootPackage = rootPackage
) {
    override val fileName: String
        get() = "Action"

    override fun createContent(): String =
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