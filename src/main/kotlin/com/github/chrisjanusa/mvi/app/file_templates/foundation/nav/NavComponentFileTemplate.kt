package com.github.chrisjanusa.mvi.app.file_templates.foundation.nav

import com.github.chrisjanusa.mvi.foundation.FileTemplate

internal class NavComponentFileTemplate : FileTemplate("NavComponent") {
    override fun createContent(rootPackage: String): String =
        "import androidx.compose.animation.AnimatedContentScope\n" +
                "import androidx.compose.animation.AnimatedContentTransitionScope\n" +
                "import androidx.compose.animation.EnterTransition\n" +
                "import androidx.compose.animation.ExitTransition\n" +
                "import androidx.compose.animation.SizeTransform\n" +
                "import androidx.compose.runtime.Composable\n" +
                "import androidx.compose.runtime.remember\n" +
                "import androidx.core.bundle.Bundle\n" +
                "import androidx.navigation.NavBackStackEntry\n" +
                "import androidx.navigation.NavDeepLink\n" +
                "import androidx.navigation.NavGraphBuilder\n" +
                "import androidx.navigation.NavHostController\n" +
                "import androidx.navigation.NavType\n" +
                "import androidx.navigation.compose.ComposeNavigator\n" +
                "import androidx.navigation.compose.ComposeNavigatorDestinationBuilder\n" +
                "import androidx.navigation.compose.navigation\n" +
                "import androidx.navigation.get\n" +
                "import androidx.navigation.serialization.decodeArguments\n" +
                "import $rootPackage.foundation.AppAction\n" +
                "import $rootPackage.foundation.viewmodel.ParentViewModel\n" +
                "import $rootPackage.foundation.viewmodel.SharedViewModel\n" +
                "import kotlinx.serialization.InternalSerializationApi\n" +
                "import kotlinx.serialization.serializer\n" +
                "import org.koin.compose.viewmodel.koinViewModel\n" +
                "import org.koin.core.parameter.parametersOf\n" +
                "import org.koin.core.qualifier.named\n" +
                "import kotlin.jvm.JvmSuppressWildcards\n" +
                "import kotlin.reflect.KClass\n" +
                "import kotlin.reflect.KType\n" +
                "\n" +
                "abstract class NavComponent(\n" +
                "    internal open val componentClass: KClass<*>\n" +
                ") {\n" +
                "    abstract fun createNavGraph(navGraphBuilder: NavGraphBuilder, navHostController: NavHostController, onAppAction: (AppAction) -> Unit, parentSharedViewModelName: String? = null)\n" +
                "}\n" +
                "\n" +
                "abstract class NavGraph(\n" +
                "    private val startDestination: NavComponent,\n" +
                "    private val destinations: List<NavComponent>,\n" +
                "    override val componentClass: KClass<*>,\n" +
                "    private val sharedViewModelName: String? = null,\n" +
                "): NavComponent(componentClass) {\n" +
                "\n" +
                "    override fun createNavGraph(navGraphBuilder: NavGraphBuilder, navHostController: NavHostController, onAppAction: (AppAction) -> Unit, parentSharedViewModelName: String?) {\n" +
                "        navGraphBuilder.run {\n" +
                "            navigation(\n" +
                "                route = componentClass,\n" +
                "                startDestination = startDestination.componentClass\n" +
                "            ) {\n" +
                "                destinations.forEach { destination ->\n" +
                "                    destination.createNavGraph(this, navHostController, onAppAction, sharedViewModelName ?: parentSharedViewModelName)\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "abstract class NavDestination(\n" +
                "    override val componentClass: KClass<*>\n" +
                "): NavComponent(componentClass) {\n" +
                "\n" +
                "    @Composable\n" +
                "    abstract fun showDestinationContent(args: Any, onAppAction: (AppAction) -> Unit, parentViewModel: ParentViewModel?)\n" +
                "\n" +
                "    override fun createNavGraph(navGraphBuilder: NavGraphBuilder, navHostController: NavHostController, onAppAction: (AppAction) -> Unit, parentSharedViewModelName: String?) {\n" +
                "        navGraphBuilder.run {\n" +
                "            composable(\n" +
                "                route = componentClass\n" +
                "            ) { backStackEntry ->\n" +
                "                val parentViewModel = parentSharedViewModelName?.let { backStackEntry.sharedKoinViewModel(it, navHostController, onAppAction) } as ParentViewModel\n" +
                "                val args = backStackEntry.toRoute(componentClass)\n" +
                "                showDestinationContent(args = args, onAppAction = onAppAction, parentViewModel = parentViewModel)\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "fun NavGraphBuilder.composable(\n" +
                "    route: KClass<*>,\n" +
                "    typeMap: Map<KType, NavType<*>> = emptyMap(),\n" +
                "    deepLinks: List<NavDeepLink> = emptyList(),\n" +
                "    enterTransition:\n" +
                "    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards\n" +
                "    EnterTransition?)? =\n" +
                "        null,\n" +
                "    exitTransition:\n" +
                "    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards\n" +
                "    ExitTransition?)? =\n" +
                "        null,\n" +
                "    popEnterTransition:\n" +
                "    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards\n" +
                "    EnterTransition?)? =\n" +
                "        enterTransition,\n" +
                "    popExitTransition:\n" +
                "    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards\n" +
                "    ExitTransition?)? =\n" +
                "        exitTransition,\n" +
                "    sizeTransform:\n" +
                "    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards\n" +
                "    SizeTransform?)? =\n" +
                "        null,\n" +
                "    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit\n" +
                ") {\n" +
                "    destination(\n" +
                "        ComposeNavigatorDestinationBuilder(\n" +
                "            provider[ComposeNavigator::class],\n" +
                "            route,\n" +
                "            typeMap,\n" +
                "            content\n" +
                "        )\n" +
                "            .apply {\n" +
                "                deepLinks.forEach { deepLink -> deepLink(deepLink) }\n" +
                "                this.enterTransition = enterTransition\n" +
                "                this.exitTransition = exitTransition\n" +
                "                this.popEnterTransition = popEnterTransition\n" +
                "                this.popExitTransition = popExitTransition\n" +
                "                this.sizeTransform = sizeTransform\n" +
                "            }\n" +
                "    )\n" +
                "}\n" +
                "\n" +
                "@OptIn(InternalSerializationApi::class)\n" +
                "private fun NavBackStackEntry.toRoute(kClass: KClass<*>): Any {\n" +
                "    val bundle = arguments ?: Bundle()\n" +
                "    val typeMap = destination.arguments.mapValues { it.value.type }\n" +
                "    return kClass.serializer().decodeArguments(bundle, typeMap)\n" +
                "}\n" +
                "\n" +
                "@Composable\n" +
                "private fun  NavBackStackEntry.sharedKoinViewModel(\n" +
                "    viewModelName: String,\n" +
                "    navHostController: NavHostController,\n" +
                "    onAppAction: (AppAction) -> Unit\n" +
                "): SharedViewModel<*> {\n" +
                "    val navGraphRoute = destination.parent?.route ?: return koinViewModel<SharedViewModel<*>>(\n" +
                "        qualifier = named(viewModelName),\n" +
                "        parameters = { parametersOf(onAppAction) },\n" +
                "    )\n" +
                "    val parentEntry = remember(this) {\n" +
                "        navHostController.getBackStackEntry(navGraphRoute)\n" +
                "    }\n" +
                "    return koinViewModel(\n" +
                "        qualifier = named(viewModelName),\n" +
                "        viewModelStoreOwner = parentEntry,\n" +
                "        parameters = { parametersOf(onAppAction) },\n" +
                "    )\n" +
                "}\n"
}