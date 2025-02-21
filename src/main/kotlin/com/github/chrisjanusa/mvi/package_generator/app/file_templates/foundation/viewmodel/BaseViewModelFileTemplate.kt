package com.github.chrisjanusa.mvi.package_generator.app.file_templates.foundation.viewmodel

import com.github.chrisjanusa.mvi.helper.file_creation.FileTemplate
import com.intellij.openapi.actionSystem.AnActionEvent

internal class BaseViewModelFileTemplate(
    actionEvent: AnActionEvent,
    rootPackage: String
) : FileTemplate(
    actionEvent = actionEvent,
    rootPackage = rootPackage
) {
    override val fileName: String
        get() = "BaseViewModel"

    override fun createContent(): String =
                "import androidx.lifecycle.ViewModel\n" +
                        "import androidx.lifecycle.viewModelScope\n" +
                        "import $rootPackage.foundation.Action\n" +
                        "import $rootPackage.foundation.Effect\n" +
                        "import $rootPackage.foundation.SliceUpdate\n" +
                        "import $rootPackage.foundation.OnAppAction\n" +
                        "import $rootPackage.foundation.state.Slice\n" +
                        "import $rootPackage.foundation.state.State\n" +
                        "import kotlinx.coroutines.flow.MutableSharedFlow\n" +
                        "import kotlinx.coroutines.flow.MutableStateFlow\n" +
                        "import kotlinx.coroutines.flow.StateFlow\n" +
                        "import kotlinx.coroutines.flow.asStateFlow\n" +
                        "import kotlinx.coroutines.flow.update\n" +
                        "import kotlinx.coroutines.launch\n" +
                        "\n" +
                        "abstract class BaseViewModel<ViewModelState: State, ViewModelSlice: Slice>(private val onAppAction: OnAppAction): ViewModel() {\n" +
                        "\n" +
                        "    abstract val effectList: List<Effect<ViewModelState, ViewModelSlice>>\n" +
                        "\n" +
                        "    abstract val initialState : ViewModelState\n" +
                        "    private val _state by lazy { MutableStateFlow(initialState) }\n" +
                        "    internal val state by lazy { _state.asStateFlow() }\n" +
                        "\n" +
                        "    abstract val slice: StateFlow<ViewModelSlice>\n" +
                        "\n" +
                        "    private val actionChannel = MutableSharedFlow<Action>()\n" +
                        "\n" +
                        "    open fun onAction(action: Action) {\n" +
                        "        viewModelScope.launch {\n" +
                        "            actionChannel.emit(action)\n" +
                        "        }\n" +
                        "\n" +
                        "        _state.update {\n" +
                        "            reduce(action, state.value, slice.value)\n" +
                        "        }\n" +
                        "    }\n" +
                        "\n" +
                        "    abstract fun reduce(action: Action, state: ViewModelState, slice: ViewModelSlice) : ViewModelState\n" +
                        "\n" +
                        "    open fun onSliceUpdate(sliceUpdate: SliceUpdate) {}\n" +
                        "\n" +
                        "    protected fun triggerEffects() {\n" +
                        "        viewModelScope.launch {\n" +
                        "            effectList.forEach { effect ->\n" +
                        "                launch { effect.launchEffect(actionChannel, state, slice, { onAppAction(it) }, { onAction(it) }, { onSliceUpdate(it) }) }\n" +
                        "            }\n" +
                        "        }\n" +
                        "    }\n" +
                        "}\n"
}