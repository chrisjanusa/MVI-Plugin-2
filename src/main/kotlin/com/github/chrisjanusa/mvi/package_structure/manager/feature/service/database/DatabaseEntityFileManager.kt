package com.github.chrisjanusa.mvi.package_structure.manager.feature.service.database

import com.github.chrisjanusa.mvi.helper.file_helper.toPascalCase
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticSuffixChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.base.ModelFileManager
import com.intellij.openapi.vfs.VirtualFile

class DatabaseEntityFileManager(
    file: VirtualFile,
    featureDatabaseFileManager: FeatureDatabaseFileManager = FeatureDatabaseFileManager(file)
) : ModelFileManager(file), IFeatureDatabaseFileManager by featureDatabaseFileManager {

    override val modelName: String
        get() = name.substringBefore(SUFFIX)
    override val typeName: String
        get() = SUFFIX

    companion object : StaticSuffixChildInstanceCompanion("Entity", DatabasePackage) {
        override fun createInstance(virtualFile: VirtualFile) = DatabaseEntityFileManager(virtualFile)
        fun getFileName(entityName: String) = "${entityName.toPascalCase()}$SUFFIX"

        fun createNewInstance(insertionPackage: DatabasePackage, entityName: String): DatabaseEntityFileManager? {
            val fileName = getFileName(entityName)
            return insertionPackage.createNewFile(
                fileName,
                DatabaseEntityTemplate(
                    packageManager = insertionPackage,
                    fileName = fileName,
                ).createContent()
            )?.let { DatabaseEntityFileManager(it) }
        }
    }
}