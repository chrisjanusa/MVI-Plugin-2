package com.github.chrisjanusa.mvi.package_structure.manager.app

import com.github.chrisjanusa.mvi.package_structure.Manager
import com.github.chrisjanusa.mvi.package_structure.manager.base.Template

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
        "import ${rootPackage?.appPackage?.navEffect?.packagePathExcludingFile}.OnBackClickEffect\n" +
        "import ${rootPackage?.appPackage?.navManager?.packagePath}\n" +
        "import ${rootPackage?.foundationPackage?.action?.packagePathExcludingFile}.AppAction\n" +
        "import ${rootPackage?.foundationPackage?.effect?.packagePathExcludingFile}.AppEffect\n" +
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