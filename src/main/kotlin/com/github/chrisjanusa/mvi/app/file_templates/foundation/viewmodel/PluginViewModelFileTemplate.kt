package com.github.chrisjanusa.mvi.app.file_templates.foundation.viewmodel

import com.github.chrisjanusa.mvi.foundation.FileTemplate
import com.intellij.openapi.actionSystem.AnActionEvent

internal class PluginViewModelFileTemplate(
    actionEvent: AnActionEvent,
    rootPackage: String
) : FileTemplate(
    actionEvent = actionEvent,
    rootPackage = rootPackage
) {
    override val fileName: String
        get() = "PluginViewModel"

    override fun createContent(): String =
                "import androidx.lifecycle.viewModelScope\n" +
                        "import $rootPackage.foundation.Action\n" +
                        "import $rootPackage.foundation.OnAppAction\n" +
                        "import $rootPackage.foundation.state.State\n" +
                        "import $rootPackage.foundation.state.Slice\n" +
                        "import kotlinx.coroutines.flow.Flow\n" +
                        "import kotlinx.coroutines.flow.MutableStateFlow\n" +
                        "import kotlinx.coroutines.flow.asStateFlow\n" +
                        "import kotlinx.coroutines.flow.collectLatest\n" +
                        "import kotlinx.coroutines.flow.update\n" +
                        "import kotlinx.coroutines.launch\n" +
                        "\n" +
                        "abstract class PluginViewModel<PluginState : State, PluginSlice : Slice>(\n" +
                        "    onAppAction: OnAppAction,\n" +
                        "    private val parentViewModel: ParentViewModel?\n" +
                        ") : BaseViewModel<PluginState, PluginSlice>(onAppAction) {\n" +
                        "    abstract val initialSlice: PluginSlice\n" +
                        "    private val _slice by lazy { MutableStateFlow(initialSlice) }\n" +
                        "    override val slice by lazy { _slice.asStateFlow() }\n" +
                        "\n" +
                        "    fun monitorSliceUpdates() {\n" +
                        "        viewModelScope.launch {\n" +
                        "            getSliceFlow()?.collectLatest { newSlice ->\n" +
                        "                _slice.update {\n" +
                        "                    newSlice\n" +
                        "                }\n" +
                        "            }\n" +
                        "        }\n" +
                        "    }\n" +
                        "\n" +
                        "    abstract fun getSliceFlow(): Flow<PluginSlice>?\n" +
                        "\n" +
                        "    override fun onAction(action: Action) {\n" +
                        "        super.onAction(action)\n" +
                        "        parentViewModel?.onChildAction(action)\n" +
                        "    }\n" +
                        "}\n"
}