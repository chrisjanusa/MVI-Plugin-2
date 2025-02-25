package com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.generated

import com.github.chrisjanusa.mvi.package_structure.Manager
import com.github.chrisjanusa.mvi.package_structure.manager.base.Template
import com.github.chrisjanusa.mvi.package_structure.manager.base.addIf
import com.github.chrisjanusa.mvi.package_structure.manager.foundation.FoundationSliceFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.foundation.FoundationStateFileManager

internal class PluginViewModelTemplate(
    private val hasState: Boolean,
    private val hasSlice: Boolean,
    packageManager: Manager,
    fileName: String
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {

    override fun createContent(): String {
        val state = if (hasState) { pluginPackage?.state?.name ?: "plugin not found" } else FoundationStateFileManager.NO_STATE
        val slice = if (hasSlice) { pluginPackage?.slice?.name ?: "plugin not found" } else FoundationSliceFileManager.NO_SLICE
        return "import ${foundationPackage?.action?.packagePath}\n" +
                "import ${foundationPackage?.parentViewModel?.packagePath}\n" +
                "import ${foundationPackage?.parentViewModel?.packagePathExcludingFile}.getFilteredSliceUpdateFlow\n".addIf { hasSlice } +
                "import ${pluginPackage?.pluginAction?.packagePath}\n" +
                "import ${foundationPackage?.action?.packagePathExcludingFile}.OnAppAction\n" +
                "import ${foundationPackage?.pluginViewModel?.packagePath}\n" +
                "import kotlinx.coroutines.flow.map\n".addIf { hasSlice }  +
                "import ${pluginPackage?.slice?.packagePath}\n".addIf { hasSlice } +
                "import ${foundationPackage?.slice?.noSlicePackagePath}\n".addIf { !hasSlice } +
                "import ${pluginPackage?.state?.packagePath}\n".addIf { hasState } +
                "import ${foundationPackage?.state?.packagePathExcludingFile}.NoState\n".addIf { !hasState } +
                "\n" +
                "class $fileName(onAppAction: OnAppAction, private val parentViewModel: ${foundationPackage?.parentViewModel}?)" +
                " : PluginViewModel<${state}, ${slice}>(onAppAction, parentViewModel) {\n" +
                "    override val initialState = ${state}()\n".addIf { hasState } +
                "    override val initialSlice = ${slice}\n".addIf { !hasSlice } +
                "    override val initialSlice = ${slice}()\n".addIf { hasSlice } +
                "    override fun getSliceFlow() =\n".addIf { hasSlice } +
                "        parentViewModel?.getFilteredSliceUpdateFlow<${pluginPackage?.sliceUpdate}>()?.map { it.slice }\n".addIf { hasSlice } +
                "    override fun getSliceFlow(): Flow<${slice}>? = null".addIf { !hasSlice } +
                "\n" +
                "\n" +
                "    override fun reduce(action: ${foundationPackage?.action}, state: ${state}, slice: ${slice}) =\n" +
                "        when (action) {\n" +
                "            is ${pluginPackage?.pluginAction} -> action.reduce(state, slice)\n" +
                "            else -> state\n" +
                "        }\n" +
                "\n" +
                "\n" +
                "    override val effectList: List<${pluginPackage?.pluginEffect}> = listOf(\n" +
                "    )\n" +
                "\n" +
                "    init {\n" +
                "        triggerEffects()\n" +
                "        monitorSliceUpdates()\n" +
                "    }\n" +
                "}\n"
    }
}