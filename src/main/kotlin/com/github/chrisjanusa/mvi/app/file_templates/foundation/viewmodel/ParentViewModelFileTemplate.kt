package com.github.chrisjanusa.mvi.app.file_templates.foundation.viewmodel

import com.github.chrisjanusa.mvi.foundation.FileTemplate

class ParentViewModelFileTemplate : FileTemplate("ParentViewModel") {
    override fun createContent(rootPackage: String): String =
                "import $rootPackage.foundation.Action\n" +
                        "import $rootPackage.foundation.SliceUpdate\n" +
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