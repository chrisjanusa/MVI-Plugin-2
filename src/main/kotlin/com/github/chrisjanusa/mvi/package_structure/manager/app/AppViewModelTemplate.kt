package com.github.chrisjanusa.mvi.package_structure.manager.app

import com.github.chrisjanusa.mvi.package_structure.manager.base.Template
import com.github.chrisjanusa.mvi.package_structure.Manager

internal class AppViewModelTemplate(
    packageManager: Manager,
    fileName: String
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {

    override fun createContent(): String =
                "import androidx.lifecycle.ViewModel\n" +
                        "import androidx.lifecycle.viewModelScope\n" +
                        "import $rootPackagePath.app.effect.OnBackClickEffect\n" +
                        "import $rootPackagePath.app.nav.NavManager\n" +
                        "import $rootPackagePath.foundation.AppAction\n" +
                        "import $rootPackagePath.foundation.AppEffect\n" +
                        "import kotlinx.coroutines.flow.MutableSharedFlow\n" +
                        "import kotlinx.coroutines.launch\n" +
                        "\n" +
                        "class $fileName(private val navManager: NavManager): ViewModel() {\n" +
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