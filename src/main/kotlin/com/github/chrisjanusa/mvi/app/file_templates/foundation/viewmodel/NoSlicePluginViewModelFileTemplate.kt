package com.github.chrisjanusa.mvi.app.file_templates.foundation.viewmodel

import com.github.chrisjanusa.mvi.foundation.FileTemplate

class NoSlicePluginViewModelFileTemplate : FileTemplate("NoSlicePluginViewModel") {
    override fun createContent(rootPackage: String): String =
                "import $rootPackage.foundation.OnAppAction\n" +
                        "import $rootPackage.foundation.state.NoSlice\n" +
                        "import $rootPackage.foundation.state.State\n" +
                        "import kotlinx.coroutines.flow.Flow\n" +
                        "import kotlinx.coroutines.flow.MutableStateFlow\n" +
                        "import kotlinx.coroutines.flow.asStateFlow\n" +
                        "\n" +
                        "abstract class NoSlicePluginViewModel<PluginState : State>(onAppAction: OnAppAction, parentViewModel: ParentViewModel?) : PluginViewModel<PluginState, NoSlice>(onAppAction, parentViewModel) {\n" +
                        "    private val _slice by lazy { MutableStateFlow(NoSlice) }\n" +
                        "    override val slice by lazy { _slice.asStateFlow() }\n" +
                        "    override val initialSlice = NoSlice\n" +
                        "\n" +
                        "    override fun getSliceFlow(): Flow<NoSlice>? = null\n" +
                        "}\n"
}