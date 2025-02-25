package com.github.chrisjanusa.mvi.package_structure.manager.foundation.viewmodel

import com.github.chrisjanusa.mvi.package_structure.manager.base.Template
import com.github.chrisjanusa.mvi.package_structure.Manager

internal class BaseViewModelTemplate(
    packageManager: Manager,
    fileName: String
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {

    override fun createContent(): String =
                "import androidx.lifecycle.ViewModel\n" +
                        "import androidx.lifecycle.viewModelScope\n" +
                        "import ${foundationPackage?.action?.packagePath}\n" +
                        "import ${foundationPackage?.effect?.packagePath}\n" +
                        "import ${foundationPackage?.sliceUpdate?.packagePath}\n" +
                        "import ${foundationPackage?.action?.packagePathExcludingFile}.OnAppAction\n" +
                        "import ${foundationPackage?.slice?.packagePath}\n" +
                        "import ${foundationPackage?.state?.packagePath}\n" +
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