package com.github.chrisjanusa.mvi.package_structure.manager.feature.nav

import com.github.chrisjanusa.mvi.package_structure.Manager
import com.github.chrisjanusa.mvi.package_structure.manager.base.Template

internal class NavGraphTemplate(
    packageManager: Manager,
    fileName: String
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {

    override fun createContent(): String {
        return "import ${rootPackage?.foundationPackage?.navComponent?.packagePathExcludingFile}.NavGraph\n" +
        "import ${rootPackage?.foundationPackage?.navComponentId?.packagePath}\n" +
        "import kotlinx.serialization.Serializable\n" +
        "\n" +
        "object $fileName: NavGraph(\n" +
        "    startDestination = // TODO add start destination,\n" +
        "    destinations = listOf(\n" +
        "    ),\n" +
        "    componentClass = ${fileName}ComponentId::class\n" +
        ")\n" +
        "\n" +
        "@Serializable\n" +
        "data object ${fileName}ComponentId : ${rootPackage?.foundationPackage?.navComponentId}"
    }
}

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