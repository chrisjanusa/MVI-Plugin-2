package com.github.chrisjanusa.mvi.package_structure.manager.app

import com.github.chrisjanusa.mvi.package_structure.Manager
import com.github.chrisjanusa.mvi.package_structure.manager.base.Template

internal class AppActivityTemplate(
    packageManager: Manager,
    fileName: String
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {
    val appName = fileName.substringBefore(AppActivityFileManager.SUFFIX)

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
        "import ${rootPackage?.appPackage?.navPackage?.navManager?.packagePath}\n" +
        "import ${rootPackage?.uiPackage?.themePackage?.packagePath}.${appName}Theme\n" +
        "import org.koin.compose.viewmodel.koinViewModel\n" +
        "import org.koin.core.parameter.parametersOf\n" +
        "\n" +
        "class $fileName : ComponentActivity() {\n" +
        "    override fun onCreate(savedInstanceState: Bundle?) {\n" +
        "        super.onCreate(savedInstanceState)\n" +
        "        enableEdgeToEdge()\n" +
        "        setContent {\n" +
        "            ${appName}Theme {\n" +
        "                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->\n" +
        "                    val navController = rememberNavController()\n" +
        "                    val navManager = ${rootPackage?.appPackage?.navPackage?.navManager?.name}(navController)\n" +
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
        "        viewModel: ${rootPackage?.appPackage?.viewModel?.name} = koinViewModel(\n" +
        "            parameters = { parametersOf(navManager) }\n" +
        "        ),\n" +
        "    ) {\n" +
        "        NavHost(\n" +
        "            modifier = Modifier.padding(innerPadding),\n" +
        "            navController = navController,\n" +
        "            startDestination = // TODO: Set initial destination,\n" +
        "        ) {\n" +
        "        }\n" +
        "    }\n" +
        "}\n"
}