package com.github.chrisjanusa.mvi.package_structure.manager.feature.service.mapper

import com.github.chrisjanusa.mvi.helper.file_helper.toPascalCase
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticSuffixChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.manager.base.ModelFileManager
import com.intellij.openapi.vfs.VirtualFile

class MapperFileManager(
    file: VirtualFile,
) : FileManager(file) {

    fun addMapper(from: ModelFileManager, to: ModelFileManager) {
        val fromPropertyNames = from.getAllProperties().keys
        val toPropertyNames = to.getAllProperties().keys
        val commonProperties = fromPropertyNames.intersect(toPropertyNames)
        if (!documentText.contains("$from.to$to()")) {
            addToBottom(
                "internal fun $from.to${to.typeName}(): $to {\n" +
                "    return $to(\n" +
                commonProperties.joinToString(separator = "") {
                    "        $it = $it,\n"
                } +
                "    )\n" +
                "}"
            )
            addImport(from.packagePath)
            addImport(to.packagePath)
        }
    }

    companion object : StaticSuffixChildInstanceCompanion(suffix = "Mapper", FeatureMapperPackage) {
        override fun createInstance(virtualFile: VirtualFile) = MapperFileManager(virtualFile)
        fun getFileName(modelName: String) = "${modelName.toPascalCase()}$SUFFIX"
        fun createNewInstance(insertionPackage: FeatureMapperPackage, modelName: String): MapperFileManager? {
            val fileName = getFileName(modelName)
            return insertionPackage.createNewFile(
                fileName,
                MapperTemplate(
                    packageManager = insertionPackage,
                    fileName = fileName
                ).createContent()
            )?.let { MapperFileManager(it) }
        }
    }
}