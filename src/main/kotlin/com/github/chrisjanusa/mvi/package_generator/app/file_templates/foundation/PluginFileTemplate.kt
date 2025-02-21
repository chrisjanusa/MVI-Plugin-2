package com.github.chrisjanusa.mvi.package_generator.app.file_templates.foundation

import com.github.chrisjanusa.mvi.helper.file_creation.FileTemplate
import com.intellij.openapi.actionSystem.AnActionEvent

internal class PluginFileTemplate(
    actionEvent: AnActionEvent,
    rootPackage: String
) : FileTemplate(
    actionEvent = actionEvent,
    rootPackage = rootPackage
) {
    override val fileName: String
        get() = "Plugin"

    override fun createContent(): String =
                "import androidx.compose.foundation.layout.fillMaxSize\n" +
                        "import androidx.compose.runtime.Composable\n" +
                        "import androidx.compose.runtime.getValue\n" +
                        "import androidx.compose.ui.Modifier\n" +
                        "import androidx.lifecycle.compose.collectAsStateWithLifecycle\n" +
                        "import $rootPackage.foundation.viewmodel.ParentViewModel\n" +
                        "import $rootPackage.foundation.state.State\n" +
                        "import $rootPackage.foundation.state.Slice\n" +
                        "import $rootPackage.foundation.viewmodel.PluginViewModel\n" +
                        "import org.koin.core.parameter.parametersOf\n" +
                        "import org.koin.compose.viewmodel.koinViewModel\n" +
                        "import org.koin.core.qualifier.named\n" +
                        "\n" +
                        "abstract class Plugin<PluginState : State, PluginSlice : Slice>(private val viewModelName: String) {\n" +
                        "    @Composable fun PluginContent(\n" +
                        "        parentViewModel: ParentViewModel? = null,\n" +
                        "        onAppAction: OnAppAction,\n" +
                        "        viewModel: PluginViewModel<PluginState, PluginSlice> = koinViewModel(\n" +
                        "            qualifier = named(viewModelName),\n" +
                        "            parameters = { parametersOf(onAppAction, parentViewModel) }\n" +
                        "        ),\n" +
                        "        modifier: Modifier = Modifier.fillMaxSize()\n" +
                        "    ) {\n" +
                        "        val state by viewModel.state.collectAsStateWithLifecycle()\n" +
                        "        val slice by viewModel.slice.collectAsStateWithLifecycle()\n" +
                        "        Content(\n" +
                        "            modifier = modifier,\n" +
                        "            state = state,\n" +
                        "            slice = slice,\n" +
                        "            onAction = { action ->\n" +
                        "                viewModel.onAction(action)\n" +
                        "            }\n" +
                        "        )\n" +
                        "\n" +
                        "    }\n" +
                        "\n" +
                        "    @Composable abstract fun Content(modifier: Modifier, state: PluginState, slice: PluginSlice, onAction: OnAction)\n" +
                        "}\n"
}