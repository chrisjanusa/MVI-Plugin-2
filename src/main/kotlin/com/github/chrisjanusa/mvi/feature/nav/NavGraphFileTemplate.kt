package com.github.chrisjanusa.mvi.feature.nav

import com.github.chrisjanusa.mvi.file_managment.toPascalCase
import com.github.chrisjanusa.mvi.foundation.FileTemplate

internal class NavGraphFileTemplate(private val featureName: String): FileTemplate("${featureName.toPascalCase()}NavGraph") {
    override fun createContent(rootPackage: String): String =
                "import $rootPackage.foundation.nav.NavGraph\n" +
                "import $rootPackage.foundation.nav.NavComponentId\n" +
                "import kotlinx.serialization.Serializable\n" +
                "\n" +
                "object ${featureName.toPascalCase()}NavGraph: NavGraph(\n" +
                "    startDestination = // TODO add start destination,\n" +
                "    destinations = listOf(\n" +
                "    ),\n" +
                "    componentClass = ${featureName.toPascalCase()}GraphNavComponentId::class\n" +
                "    )\n" +
                ")\n" +
                "\n" +
                "@Serializable\n" +
                "data object ${featureName.toPascalCase()}GraphNavComponentId : NavComponentId"

//    fun generateBookNavGraph(rootPackage: String, featureName: String): FileSpec {
//        val fileSpec = FileSpec.builder(
//            "$rootPackage.$featureName.plugin",
//            "BookNavGraph"
//        )
//
//        val navComponentIdClassName = ClassName(
//            "$rootPackage.foundation.nav",
//            "NavComponentId"
//        )
//
//        val navGraphClassName = ClassName(
//            "$rootPackage.foundation.nav",
//            "NavGraph"
//        )
//
//        val bookListNavDestinationClassName = ClassName(
//            "$rootPackage.$featureName.plugin.book_list.generated",
//            "BookListNavDestination"
//        )
//
//        val bookDetailsNavDestinationClassName = ClassName(
//            "$rootPackage.$featureName.plugin.book_details.generated",
//            "BookDetailsNavDestination"
//        )
//
//        val bookNavGraphObject = TypeSpec.objectBuilder("BookNavGraph")
//            .superclass(
//                navGraphClassName.parameterizedBy(navComponentIdClassName)
//            )
//            .addSuperclassConstructorParameter("startDestination = %T", bookListNavDestinationClassName)
//            .addSuperclassConstructorParameter("destinations = listOf(%T, %T)", bookListNavDestinationClassName, bookDetailsNavDestinationClassName)
//            .addSuperclassConstructorParameter("componentClass = %T::class", ClassName("$rootPackage.$featureName.plugin", "BookGraphNavComponentId"))
//            .build()
//
//        val serializableClassName = ClassName("kotlinx.serialization", "Serializable")
//        val bookGraphNavComponentIdObject = TypeSpec.objectBuilder("BookGraphNavComponentId")
//            .addSuperinterface(navComponentIdClassName)
//            .addAnnotation(serializableClassName)
//            .addModifiers(KModifier.DATA)
//            .build()
//
//        fileSpec.addType(bookNavGraphObject)
//            .addType(bookGraphNavComponentIdObject)
//
//        return fileSpec.build()
//    }
}