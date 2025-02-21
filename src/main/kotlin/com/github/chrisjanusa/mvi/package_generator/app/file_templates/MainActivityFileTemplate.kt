package com.github.chrisjanusa.mvi.package_generator.app.file_templates

import com.github.chrisjanusa.mvi.helper.file_creation.FileTemplate
import com.intellij.openapi.actionSystem.AnActionEvent

internal class MainActivityFileTemplate(
    private val appName: String,
    actionEvent: AnActionEvent,
    rootPackage: String,
) : FileTemplate(
    actionEvent = actionEvent,
    rootPackage = rootPackage
) {
    override val fileName: String
        get() = "MainActivity"

    override fun createContent(): String =
                "import android.os.Bundle\n" +
                        "import androidx.activity.ComponentActivity\n" +
                        "import androidx.activity.compose.setContent\n" +
                        "import androidx.activity.enableEdgeToEdge\n" +
                        "import androidx.compose.foundation.layout.PaddingValues\n" +
                        "import androidx.compose.foundation.layout.fillMaxSize\n" +
                        "import androidx.compose.foundation.layout.padding\n" +
                        "import androidx.compose.material3.Scaffold\n" +
                        "import androidx.compose.runtime.Composable\n" +
                        "import androidx.compose.ui.Modifier\n" +
                        "import androidx.navigation.NavHostController\n" +
                        "import androidx.navigation.compose.NavHost\n" +
                        "import androidx.navigation.compose.rememberNavController\n" +
                        "import $rootPackage.app.nav.NavManager\n" +
                        "import $rootPackage.common.helper.getClassName\n" +
                        "import $rootPackage.ui.theme.${appName}Theme\n" +
                        "import org.koin.compose.viewmodel.koinViewModel\n" +
                        "import org.koin.core.parameter.parametersOf\n" +
                        "\n" +
                        "class MainActivity : ComponentActivity() {\n" +
                        "    override fun onCreate(savedInstanceState: Bundle?) {\n" +
                        "        super.onCreate(savedInstanceState)\n" +
                        "        enableEdgeToEdge()\n" +
                        "        setContent {\n" +
                        "            ${appName}Theme {\n" +
                        "                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->\n" +
                        "                    val navController = rememberNavController()\n" +
                        "                    val navManager = NavManager(navController)\n" +
                        "                    AppNavigation(innerPadding, navController, navManager)\n" +
                        "                }\n" +
                        "            }\n" +
                        "        }\n" +
                        "    }\n" +
                        "\n" +
                        "    @Composable\n" +
                        "    fun AppNavigation(\n" +
                        "        innerPadding: PaddingValues,\n" +
                        "        navController: NavHostController,\n" +
                        "        navManager: NavManager,\n" +
                        "        viewModel: AppViewModel = koinViewModel(\n" +
                        "            parameters = { parametersOf(navManager) }\n" +
                        "        ),\n" +
                        "    ) {\n" +
                        "        NavHost(\n" +
                        "            modifier = Modifier.padding(innerPadding),\n" +
                        "            navController = navController,\n" +
                        "            startDestination = // TODO: Set initial destination\n" +
                        "        ) {\n" +
                        "        }\n" +
                        "    }\n" +
                        "}\n"
}