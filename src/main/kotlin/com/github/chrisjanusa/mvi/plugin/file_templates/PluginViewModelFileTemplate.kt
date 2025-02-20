package com.github.chrisjanusa.mvi.plugin.file_templates

import com.github.chrisjanusa.mvi.file_managment.toPascalCase
import com.github.chrisjanusa.mvi.foundation.FileTemplate
import com.github.chrisjanusa.mvi.foundation.addIf

internal class PluginViewModelFileTemplate(private val featureName: String, private val pluginName: String, private val hasState: Boolean, private val hasSlice: Boolean) : FileTemplate("${pluginName.toPascalCase()}ViewModel") {
    override fun createContent(rootPackage: String): String {
        val pluginCapitalized = pluginName.toPascalCase()
        val state = if (hasState) "${pluginCapitalized}State" else "NoState"
        val slice = if (hasSlice) "${pluginCapitalized}Slice" else "NoSlice"
        return "import $rootPackage.foundation.Action\n" +
                "import $rootPackage.foundation.viewmodel.ParentViewModel\n" +
                "import $rootPackage.foundation.viewmodel.getFilteredSliceUpdateFlow\n" +
                "import $rootPackage.$featureName.plugin.$pluginName.${pluginCapitalized}Action\n" +
                "import $rootPackage.foundation.OnAppAction\n" +
                "import $rootPackage.foundation.viewmodel.PluginViewModel\n" +
                "import kotlinx.coroutines.flow.map\n" +
                "import $rootPackage.$featureName.plugin.$pluginName.$slice\n".addIf { hasSlice } +
                "import $rootPackage.foundation.state.NoSlice\n".addIf { !hasSlice } +
                "import $rootPackage.$featureName.plugin.$pluginName.$state\n".addIf { hasState } +
                "import $rootPackage.foundation.state.NoState\n".addIf { !hasState } +
                "\n" +
                "class $fileName(onAppAction: OnAppAction, private val parentViewModel: ParentViewModel?)" +
                " : PluginViewModel<${state}, ${slice}>(onAppAction, parentViewModel) {\n" +
                "    override val initialState = ${state}()\n".addIf { hasState } +
                "    override val initialSlice = ${state}\n".addIf { !hasSlice } +
                "    override val initialSlice = ${slice}()\n".addIf { hasSlice } +
                "    override fun getSliceFlow() =\n".addIf { hasSlice } +
                "        parentViewModel?.getFilteredSliceUpdateFlow<${slice}Update>()?.map { it.slice }\n".addIf { hasSlice } +
                "    override val initialSlice = ${slice}\n".addIf { !hasSlice } +
                "    override fun getSliceFlow(): Flow<${slice}>? = null".addIf { !hasSlice } +
                "\n" +
                "\n" +
                "    override fun reduce(action: Action, state: ${state}, slice: ${slice}) =\n" +
                "        when (action) {\n" +
                "            is ${pluginCapitalized}Action -> action.reduce(state, slice)\n" +
                "            else -> state\n" +
                "        }\n" +
                "\n" +
                "\n" +
                "    override val effectList: List<${pluginCapitalized}Effect> = listOf(\n" +
                "    )\n" +
                "\n" +
                "    init {\n" +
                "        triggerEffects()\n" +
                "        monitorSliceUpdates()\n" +
                "    }\n" +
                "}\n"
    }
}