package com.github.chrisjanusa.mvi.package_structure.manager.foundation.viewmodel

import com.github.chrisjanusa.mvi.package_structure.manager.base.Template
import com.github.chrisjanusa.mvi.package_structure.Manager

internal class FoundationParentViewModelTemplate(
    packageManager: Manager,
    fileName: String
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {

    override fun createContent(): String =
                "import ${foundationPackage?.action?.packagePath}\n" +
                        "import ${foundationPackage?.sliceUpdate?.packagePath}\n" +
                        "import kotlinx.coroutines.flow.Flow\n" +
                        "import kotlinx.coroutines.flow.filterIsInstance\n" +
                        "\n" +
                        "interface ParentViewModel {\n" +
                        "    fun onChildAction(action: Action)\n" +
                        "    fun getSliceUpdateFlow() : Flow<SliceUpdate>\n" +
                        "}\n" +
                        "\n" +
                        "inline fun <reified ParentSliceUpdate: SliceUpdate> ParentViewModel.getFilteredSliceUpdateFlow() : Flow<ParentSliceUpdate> = getSliceUpdateFlow().filterIsInstance<ParentSliceUpdate>()\n"
}