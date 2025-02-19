package com.github.chrisjanusa.mvi.feature.nav

import com.github.chrisjanusa.mvi.file_managment.capitalize
import com.github.chrisjanusa.mvi.foundation.FileTemplate

internal class NavGraphFileTemplate(private val featureName: String): FileTemplate("${featureName.capitalize()}NavGraph") {
    override fun createContent(rootPackage: String): String =
                "import $rootPackage.foundation.nav.NavGraph\n" +
                "import $rootPackage.foundation.nav.NavComponentId\n" +
                "import kotlinx.serialization.Serializable\n" +
                "\n" +
                "object ${featureName.capitalize()}NavGraph: NavGraph(\n" +
                "    startDestination = // TODO add start destination,\n" +
                "    destinations = listOf(\n" +
                "),\n" +
                "    componentClass = ${featureName.capitalize()}GraphNavComponentId::class\n" +
                ")\n" +
                "\n" +
                "@Serializable\n" +
                "data object ${featureName.capitalize()}GraphNavComponentId : NavComponentId"
}