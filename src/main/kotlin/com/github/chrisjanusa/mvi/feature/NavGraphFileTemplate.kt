package com.github.chrisjanusa.mvi.feature

import com.github.chrisjanusa.mvi.file_managment.capitalize
import com.github.chrisjanusa.mvi.foundation.FileTemplate

class NavGraphFileTemplate(private val featureName: String): FileTemplate("${featureName.capitalize()}NavGraph") {
    override fun createContent(rootPackage: String): String =
                "import $rootPackage.foundation.nav.BaseNavGraph\n" +
                "import $rootPackage.foundation.nav.NavComponentId\n" +
                "import kotlinx.serialization.Serializable\n" +
                "\n" +
                "object ${featureName.capitalize()}NavGraph: BaseNavGraph(\n" +
                "    startDestination = // TODO add start destination,\n" +
                "    destinations = listOf(),\n" +
                "    componentClass = ${featureName.capitalize()}GraphNavComponentId::class\n" +
                ")\n" +
                "\n" +
                "@Serializable\n" +
                "data object ${featureName.capitalize()}GraphNavComponentId : NavComponentId"
}