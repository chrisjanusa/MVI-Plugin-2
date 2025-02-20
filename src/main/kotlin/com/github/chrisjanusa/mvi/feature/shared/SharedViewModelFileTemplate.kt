package com.github.chrisjanusa.mvi.feature.shared

import com.github.chrisjanusa.mvi.file_managment.toPascalCase
import com.github.chrisjanusa.mvi.foundation.FileTemplate

internal class SharedViewModelFileTemplate(private val featureName: String): FileTemplate("${featureName.toPascalCase()}SharedViewModel") {
    override fun createContent(rootPackage: String): String =
                "import $rootPackage.foundation.Action\n" +
                        "import $rootPackage.foundation.SliceUpdate\n" +
                        "import $rootPackage.$featureName.shared.${featureName.toPascalCase()}SharedAction\n" +
                        "import $rootPackage.$featureName.shared.${featureName.toPascalCase()}SharedState\n" +
                        "import $rootPackage.$featureName.shared.OnChildActionReceivedEffect\n" +
                        "import $rootPackage.foundation.OnAppAction\n" +
                        "import $rootPackage.foundation.state.NoSlice\n" +
                        "import $rootPackage.foundation.viewmodel.SharedViewModel\n" +
                        "import kotlinx.coroutines.flow.MutableSharedFlow\n" +
                        "\n" +
                        "\n" +
                        "class ${featureName.toPascalCase()}SharedViewModel(onAppAction: OnAppAction) : SharedViewModel<${featureName.toPascalCase()}SharedState>(onAppAction) {\n" +
                        "    private val sliceUpdateEffectList: List<${featureName.toPascalCase()}SharedEffect> = listOf(\n" +
                        "    )\n" +
                        "\n" +
                        "    override val effectList: List<${featureName.toPascalCase()}SharedEffect> = sliceUpdateEffectList + listOf(\n" +
                        "        OnChildActionReceivedEffect\n" +
                        "    )\n" +
                        "\n" +
                        "    override val sliceUpdateFlow = MutableSharedFlow<SliceUpdate>(replay = sliceUpdateEffectList.size)\n" +
                        "\n" +
                        "    override val initialState = ${featureName.toPascalCase()}SharedState()\n" +
                        "\n" +
                        "    init {\n" +
                        "        triggerEffects()\n" +
                        "    }\n" +
                        "\n" +
                        "    override fun reduce(action: Action, state: ${featureName.toPascalCase()}SharedState, slice: NoSlice)  =\n" +
                        "        when(action) {\n" +
                        "            is ${featureName.toPascalCase()}SharedAction -> action.reduce(state, slice)\n" +
                        "            else -> state\n" +
                        "        }\n" +
                        "\n" +
                        "    override fun onChildAction(action: Action) {\n" +
                        "        onAction(action)\n" +
                        "    }\n" +
                        "}\n"
}