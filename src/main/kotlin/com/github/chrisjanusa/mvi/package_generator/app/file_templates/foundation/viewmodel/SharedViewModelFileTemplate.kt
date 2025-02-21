package com.github.chrisjanusa.mvi.package_generator.app.file_templates.foundation.viewmodel

import com.github.chrisjanusa.mvi.helper.file_creation.FileTemplate
import com.intellij.openapi.actionSystem.AnActionEvent

internal class SharedViewModelFileTemplate(
    actionEvent: AnActionEvent,
    rootPackage: String
) : FileTemplate(
    actionEvent = actionEvent,
    rootPackage = rootPackage
) {
    override val fileName: String
        get() = "SharedViewModel"

    override fun createContent(): String =
                "import androidx.lifecycle.viewModelScope\n" +
                        "import $rootPackage.foundation.SliceUpdate\n" +
                        "import $rootPackage.foundation.OnAppAction\n" +
                        "import $rootPackage.foundation.state.NoSlice\n" +
                        "import $rootPackage.foundation.state.State\n" +
                        "import kotlinx.coroutines.flow.Flow\n" +
                        "import kotlinx.coroutines.flow.MutableSharedFlow\n" +
                        "import kotlinx.coroutines.flow.MutableStateFlow\n" +
                        "import kotlinx.coroutines.flow.asStateFlow\n" +
                        "import kotlinx.coroutines.launch\n" +
                        "\n" +
                        "abstract class SharedViewModel<SharedState: State>(onAppAction: OnAppAction): BaseViewModel<SharedState, NoSlice>(onAppAction), ParentViewModel {\n" +
                        "    abstract val sliceUpdateFlow : MutableSharedFlow<SliceUpdate>\n" +
                        "\n" +
                        "    override fun onSliceUpdate(sliceUpdate: SliceUpdate) {\n" +
                        "        viewModelScope.launch {\n" +
                        "            sliceUpdateFlow.emit(sliceUpdate)\n" +
                        "        }\n" +
                        "    }\n" +
                        "\n" +
                        "    override val slice by lazy { MutableStateFlow(NoSlice).asStateFlow() }\n" +
                        "\n" +
                        "    override fun getSliceUpdateFlow(): Flow<SliceUpdate> = sliceUpdateFlow\n" +
                        "}\n"
}