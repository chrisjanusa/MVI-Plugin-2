package com.github.chrisjanusa.mvi.package_structure.manager.foundation.viewmodel

import com.github.chrisjanusa.mvi.package_structure.manager.base.Template
import com.github.chrisjanusa.mvi.package_structure.Manager

internal class FoundationPluginViewModelTemplate(
    packageManager: Manager,
    fileName: String
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {

    override fun createContent(): String =
                "import androidx.lifecycle.viewModelScope\n" +
                        "import $rootPackagePath.foundation.Action\n" +
                        "import $rootPackagePath.foundation.OnAppAction\n" +
                        "import ${foundationPackage?.state?.packagePath}\n" +
                        "import ${foundationPackage?.slice?.packagePath}\n" +
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