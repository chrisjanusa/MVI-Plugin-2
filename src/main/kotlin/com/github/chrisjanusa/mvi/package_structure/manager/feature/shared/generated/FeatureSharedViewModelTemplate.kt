package com.github.chrisjanusa.mvi.package_structure.manager.feature.shared.generated

import com.github.chrisjanusa.mvi.package_structure.Manager
import com.github.chrisjanusa.mvi.package_structure.manager.base.Template

internal class FeatureSharedViewModelTemplate(
    packageManager: Manager,
    fileName: String
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {

    override fun createContent(): String =
        "import $rootPackagePath.foundation.Action\n" +
        "import $rootPackagePath.foundation.SliceUpdate\n" +
        "import $rootPackagePath.$featurePackageName.shared.${featureName}SharedAction\n" +
        "import $rootPackagePath.$featurePackageName.shared.${featureName}SharedState\n" +
        "import $rootPackagePath.$featurePackageName.shared.OnChildActionReceivedEffect\n" +
        "import $rootPackagePath.foundation.OnAppAction\n" +
        "import ${foundationPackage?.slice?.packagePathExcludingFile}.NoSlice\n" +
        "import ${foundationPackage?.sharedViewModel?.packagePath}\n" +
        "import kotlinx.coroutines.flow.MutableSharedFlow\n" +
        "\n" +
        "\n" +
        "class $fileName(onAppAction: OnAppAction) : SharedViewModel<${featureName}SharedState>(onAppAction) {\n" +
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