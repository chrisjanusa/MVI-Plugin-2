package com.github.chrisjanusa.mvi.feature.shared

import com.github.chrisjanusa.mvi.file_managment.capitalize
import com.github.chrisjanusa.mvi.foundation.FileTemplate

class SharedViewModelFileTemplate(private val featureName: String): FileTemplate("${featureName.capitalize()}SharedViewModel") {
    override fun createContent(rootPackage: String): String =
                "import $rootPackage.foundation.Action\n" +
                        "import $rootPackage.foundation.SliceUpdate\n" +
                        "import $rootPackage.book.shared.BookSharedAction\n" +
                        "import $rootPackage.book.shared.BookSharedState\n" +
                        "import $rootPackage.book.shared.OnChildActionReceivedEffect\n" +
                        "import $rootPackage.foundation.OnAppAction\n" +
                        "import $rootPackage.foundation.state.NoSlice\n" +
                        "import $rootPackage.foundation.viewmodel.SharedViewModel\n" +
                        "import kotlinx.coroutines.flow.MutableSharedFlow\n" +
                        "\n" +
                        "\n" +
                        "class ${featureName.capitalize()}SharedViewModel(onAppAction: OnAppAction) : SharedViewModel<${featureName.capitalize()}SharedState>(onAppAction) {\n" +
                        "    private val sliceUpdateEffectList = listOf(\n" +
                        "    )\n" +
                        "\n" +
                        "    override val effectList: List<${featureName.capitalize()}SharedEffect> = sliceUpdateEffectList + listOf(\n" +
                        "        OnChildActionReceivedEffect\n" +
                        "    )\n" +
                        "\n" +
                        "    override val sliceUpdateFlow = MutableSharedFlow<SliceUpdate>(replay = sliceUpdateEffectList.size)\n" +
                        "\n" +
                        "    override val initialState = ${featureName.capitalize()}SharedState()\n" +
                        "\n" +
                        "    init {\n" +
                        "        triggerEffects()\n" +
                        "    }\n" +
                        "\n" +
                        "    override fun reduce(action: Action, state: ${featureName.capitalize()}SharedState, slice: NoSlice)  =\n" +
                        "        when(action) {\n" +
                        "            is ${featureName.capitalize()}SharedAction -> action.reduce(state, slice)\n" +
                        "            else -> state\n" +
                        "        }\n" +
                        "\n" +
                        "    override fun onChildAction(action: Action) {\n" +
                        "        onAction(action)\n" +
                        "    }\n" +
                        "}\n"
}