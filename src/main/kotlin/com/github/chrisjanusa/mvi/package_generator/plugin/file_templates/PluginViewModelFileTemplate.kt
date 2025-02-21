package com.github.chrisjanusa.mvi.package_generator.plugin.file_templates

import com.github.chrisjanusa.mvi.helper.file_managment.getPluginPackageFile
import com.github.chrisjanusa.mvi.helper.file_creation.FileTemplate
import com.github.chrisjanusa.mvi.helper.file_creation.addIf
import com.intellij.openapi.actionSystem.AnActionEvent

internal class PluginViewModelFileTemplate(
    private val hasState: Boolean,
    private val hasSlice: Boolean,
    actionEvent: AnActionEvent,
    pluginPackage: String? = actionEvent.getPluginPackageFile()?.name
) : FileTemplate(
    actionEvent = actionEvent,
    pluginPackage = pluginPackage
) {
    override val fileName: String
        get() = "${pluginPackage}ViewModel"

    override fun createContent(): String {
        val state = if (hasState) "${pluginName}State" else "NoState"
        val slice = if (hasSlice) "${pluginName}Slice" else "NoSlice"
        return "import $rootPackage.foundation.Action\n" +
                "import $rootPackage.foundation.viewmodel.ParentViewModel\n" +
                "import $rootPackage.foundation.viewmodel.getFilteredSliceUpdateFlow\n" +
                "import $rootPackage.$featurePackage.plugin.$pluginPackage.${pluginName}Action\n" +
                "import $rootPackage.foundation.OnAppAction\n" +
                "import $rootPackage.foundation.viewmodel.PluginViewModel\n" +
                "import kotlinx.coroutines.flow.map\n" +
                "import $rootPackage.$featurePackage.plugin.$pluginPackage.$slice\n".addIf { hasSlice } +
                "import $rootPackage.foundation.state.NoSlice\n".addIf { !hasSlice } +
                "import $rootPackage.$featurePackage.plugin.$pluginPackage.$state\n".addIf { hasState } +
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
                "            is ${pluginName}Action -> action.reduce(state, slice)\n" +
                "            else -> state\n" +
                "        }\n" +
                "\n" +
                "\n" +
                "    override val effectList: List<${pluginName}Effect> = listOf(\n" +
                "    )\n" +
                "\n" +
                "    init {\n" +
                "        triggerEffects()\n" +
                "        monitorSliceUpdates()\n" +
                "    }\n" +
                "}\n"
    }
}