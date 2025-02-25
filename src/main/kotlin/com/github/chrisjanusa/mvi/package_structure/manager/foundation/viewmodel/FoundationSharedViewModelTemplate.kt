package com.github.chrisjanusa.mvi.package_structure.manager.foundation.viewmodel

import com.github.chrisjanusa.mvi.package_structure.manager.base.Template
import com.github.chrisjanusa.mvi.package_structure.Manager

internal class FoundationSharedViewModelTemplate(
    packageManager: Manager,
    fileName: String
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {

    override fun createContent(): String =
                "import androidx.lifecycle.viewModelScope\n" +
                        "import $rootPackagePath.foundation.SliceUpdate\n" +
                        "import $rootPackagePath.foundation.OnAppAction\n" +
                        "import ${foundationPackage?.slice?.packagePathExcludingFile}.NoSlice\n" +
                        "import ${foundationPackage?.state?.packagePath}\n" +
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