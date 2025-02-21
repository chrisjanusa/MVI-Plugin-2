package com.github.chrisjanusa.mvi.package_generator.app.file_templates.foundation

import com.github.chrisjanusa.mvi.helper.file_creation.FileTemplate
import com.intellij.openapi.actionSystem.AnActionEvent

internal class EffectFileTemplate(
    actionEvent: AnActionEvent,
    rootPackage: String
) : FileTemplate(
    actionEvent = actionEvent,
    rootPackage = rootPackage
) {
    override val fileName: String
        get() = "Effect"

    override fun createContent(): String =
        "import $rootPackage.foundation.state.Slice\n" +
                "import $rootPackage.foundation.state.State\n" +
                "import $rootPackage.app.nav.NavManager\n" +
                "import kotlinx.coroutines.flow.Flow\n" +
                "import kotlinx.coroutines.flow.StateFlow\n" +
                "import kotlinx.coroutines.flow.collectLatest\n" +
                "import kotlinx.coroutines.flow.combine\n" +
                "import org.koin.core.component.KoinComponent\n" +
                "\n" +
                "\n" +
                "abstract class ActionEffect<EffectState : State, EffectSlice : Slice> : Effect<EffectState, EffectSlice> {\n" +
                "    override suspend fun launchEffect(actionFlow: Flow<Action>, stateFlow: StateFlow<EffectState>, sliceFlow: StateFlow<EffectSlice>, onAppAction: OnAppAction, onAction: OnAction, onSliceUpdate: (SliceUpdate) -> Unit) {\n" +
                "        launchEffect(actionFlow, onAction)\n" +
                "    }\n" +
                "\n" +
                "    abstract suspend fun launchEffect(actionFlow: Flow<Action>, onAction: OnAction)\n" +
                "}\n" +
                "\n" +
                "abstract class StateEffect<EffectState : State, EffectSlice : Slice> : Effect<EffectState, EffectSlice> {\n" +
                "    override suspend fun launchEffect(actionFlow: Flow<Action>, stateFlow: StateFlow<EffectState>, sliceFlow: StateFlow<EffectSlice>, onAppAction: OnAppAction, onAction: OnAction, onSliceUpdate: (SliceUpdate) -> Unit) {\n" +
                "        launchEffect(actionFlow, stateFlow, onAction)\n" +
                "    }\n" +
                "\n" +
                "    abstract suspend fun launchEffect(actionFlow: Flow<Action>, stateFlow: StateFlow<EffectState>, onAction: OnAction)\n" +
                "}\n" +
                "\n" +
                "abstract class StateSliceEffect<EffectState : State, EffectSlice : Slice> : Effect<EffectState, EffectSlice> {\n" +
                "    override suspend fun launchEffect(actionFlow: Flow<Action>, stateFlow: StateFlow<EffectState>, sliceFlow: StateFlow<EffectSlice>, onAppAction: OnAppAction, onAction: OnAction, onSliceUpdate: (SliceUpdate) -> Unit) {\n" +
                "        launchEffect(actionFlow, stateFlow, sliceFlow, onAction)\n" +
                "    }\n" +
                "\n" +
                "    abstract suspend fun launchEffect(actionFlow: Flow<Action>, stateFlow: StateFlow<EffectState>, sliceFlow: StateFlow<EffectSlice>, onAction: OnAction)\n" +
                "}\n" +
                "\n" +
                "abstract class NavEffect<EffectState : State, EffectSlice : Slice> : Effect<EffectState, EffectSlice> {\n" +
                "    override suspend fun launchEffect(actionFlow: Flow<Action>, stateFlow: StateFlow<EffectState>, sliceFlow: StateFlow<EffectSlice>, onAppAction: OnAppAction, onAction: OnAction, onSliceUpdate: (SliceUpdate) -> Unit) {\n" +
                "        launchEffect(actionFlow, onAppAction, onAction)\n" +
                "    }\n" +
                "\n" +
                "    abstract suspend fun launchEffect(actionFlow: Flow<Action>, onAppAction: OnAppAction, onAction: OnAction)\n" +
                "}\n" +
                "\n" +
                "abstract class SliceUpdateEffect<EffectState : State, EffectSlice : Slice> : Effect<EffectState, EffectSlice> {\n" +
                "    override suspend fun launchEffect(actionFlow: Flow<Action>, stateFlow: StateFlow<EffectState>, sliceFlow: StateFlow<EffectSlice>, onAppAction: OnAppAction, onAction: OnAction, onSliceUpdate: (SliceUpdate) -> Unit) {\n" +
                "        stateFlow.combine(sliceFlow) { state, slice ->\n" +
                "            stateUpdateToChildSliceUpdate(state, slice)\n" +
                "        }.collectLatest { childSliceUpdate ->\n" +
                "            onSliceUpdate(childSliceUpdate)\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    abstract fun stateUpdateToChildSliceUpdate(state: EffectState, slice: EffectSlice): SliceUpdate\n" +
                "}\n" +
                "\n" +
                "interface Effect<EffectState : State, EffectSlice : Slice> : KoinComponent {\n" +
                "    suspend fun launchEffect(actionFlow: Flow<Action>, stateFlow: StateFlow<EffectState>, sliceFlow: StateFlow<EffectSlice>, onAppAction: OnAppAction, onAction: OnAction, onSliceUpdate: (SliceUpdate) -> Unit)\n" +
                "}\n" +
                "\n" +
                "interface AppEffect : KoinComponent {\n" +
                "    suspend fun launchEffect(actionFlow: Flow<AppAction>, navManager: NavManager, onAction: OnAppAction)\n" +
                "}\n"
}