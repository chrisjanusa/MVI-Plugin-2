package com.github.chrisjanusa.mvi.feature.shared

import com.github.chrisjanusa.mvi.file_managment.getFeaturePackageFile
import com.github.chrisjanusa.mvi.foundation.FileTemplate
import com.intellij.openapi.actionSystem.AnActionEvent

internal class SharedViewModelFileTemplate(
    actionEvent: AnActionEvent,
    featurePackage: String? = actionEvent.getFeaturePackageFile()?.name
): FileTemplate(
    actionEvent = actionEvent,
    featurePackage = featurePackage
) {
    override val fileName: String
        get() = "${featureName}SharedViewModel"

    override fun createContent(): String =
                "import $rootPackage.foundation.Action\n" +
                        "import $rootPackage.foundation.SliceUpdate\n" +
                        "import $rootPackage.$featurePackage.shared.${featureName}SharedAction\n" +
                        "import $rootPackage.$featurePackage.shared.${featureName}SharedState\n" +
                        "import $rootPackage.$featurePackage.shared.OnChildActionReceivedEffect\n" +
                        "import $rootPackage.foundation.OnAppAction\n" +
                        "import $rootPackage.foundation.state.NoSlice\n" +
                        "import $rootPackage.foundation.viewmodel.SharedViewModel\n" +
                        "import kotlinx.coroutines.flow.MutableSharedFlow\n" +
                        "\n" +
                        "\n" +
                        "class ${featureName}SharedViewModel(onAppAction: OnAppAction) : SharedViewModel<${featureName}SharedState>(onAppAction) {\n" +
                        "    private val sliceUpdateEffectList: List<${featureName}SharedEffect> = listOf(\n" +
                        "    )\n" +
                        "\n" +
                        "    override val effectList: List<${featureName}SharedEffect> = sliceUpdateEffectList + listOf(\n" +
                        "        OnChildActionReceivedEffect\n" +
                        "    )\n" +
                        "\n" +
                        "    override val sliceUpdateFlow = MutableSharedFlow<SliceUpdate>(replay = sliceUpdateEffectList.size)\n" +
                        "\n" +
                        "    override val initialState = ${featureName}SharedState()\n" +
                        "\n" +
                        "    init {\n" +
                        "        triggerEffects()\n" +
                        "    }\n" +
                        "\n" +
                        "    override fun reduce(action: Action, state: ${featureName}SharedState, slice: NoSlice)  =\n" +
                        "        when(action) {\n" +
                        "            is ${featureName}SharedAction -> action.reduce(state, slice)\n" +
                        "            else -> state\n" +
                        "        }\n" +
                        "\n" +
                        "    override fun onChildAction(action: Action) {\n" +
                        "        onAction(action)\n" +
                        "    }\n" +
                        "}\n"
}