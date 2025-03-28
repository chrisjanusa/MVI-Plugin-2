package com.github.chrisjanusa.mvi.package_structure.manager.foundation

import com.github.chrisjanusa.mvi.package_structure.Manager
import com.github.chrisjanusa.mvi.package_structure.manager.base.Template

internal class FoundationEffectTemplate(
    packageManager: Manager,
    fileName: String
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {

    override fun createContent(): String =
        "import ${rootPackage?.appPackage?.navManager?.packagePath}.app.nav.NavManager\n" +
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