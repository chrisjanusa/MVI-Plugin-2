package com.github.chrisjanusa.mvi.app.file_templates

import com.github.chrisjanusa.mvi.foundation.FileTemplate
import com.intellij.openapi.actionSystem.AnActionEvent

internal class ActivityViewModelFileTemplate(
    actionEvent: AnActionEvent,
    rootPackage: String
) : FileTemplate(
    actionEvent = actionEvent,
    rootPackage = rootPackage
) {
    override val fileName: String
        get() = "ActivityViewModel"

    override fun createContent(): String =
                "import androidx.lifecycle.ViewModel\n" +
                        "import androidx.lifecycle.viewModelScope\n" +
                        "import $rootPackage.app.effect.OnBackClickEffect\n" +
                        "import $rootPackage.app.nav.NavManager\n" +
                        "import $rootPackage.foundation.AppAction\n" +
                        "import $rootPackage.foundation.AppEffect\n" +
                        "import kotlinx.coroutines.flow.MutableSharedFlow\n" +
                        "import kotlinx.coroutines.launch\n" +
                        "\n" +
                        "class AppViewModel(private val navManager: NavManager): ViewModel() {\n" +
                        "\n" +
                        "    private val effectList: List<AppEffect> = listOf(\n" +
                        "        OnBackClickEffect,\n" +
                        "    )\n" +
                        "\n" +
                        "    private val actionChannel = MutableSharedFlow<AppAction>()\n" +
                        "\n" +
                        "    fun onAction(action: AppAction) {\n" +
                        "        viewModelScope.launch {\n" +
                        "            actionChannel.emit(action)\n" +
                        "        }\n" +
                        "    }\n" +
                        "\n" +
                        "    init {\n" +
                        "        viewModelScope.launch {\n" +
                        "            effectList.forEach { effect ->\n" +
                        "                launch { effect.launchEffect(actionChannel, navManager, { onAction(it) }) }\n" +
                        "            }\n" +
                        "        }\n" +
                        "    }\n" +
                        "}\n"
}