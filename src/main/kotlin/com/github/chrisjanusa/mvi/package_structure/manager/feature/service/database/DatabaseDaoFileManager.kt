package com.github.chrisjanusa.mvi.package_structure.manager.feature.service.database

import com.github.chrisjanusa.mvi.helper.file_helper.toPascalCase
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticSuffixChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.intellij.openapi.vfs.VirtualFile

class DatabaseDaoFileManager(
    file: VirtualFile,
    featureDatabaseFileManager: FeatureDatabaseFileManager = FeatureDatabaseFileManager(file)
) : FileManager(file), IFeatureDatabaseFileManager by featureDatabaseFileManager {

    companion object : StaticSuffixChildInstanceCompanion("Dao", DatabasePackage) {
        override fun createInstance(virtualFile: VirtualFile) = DatabaseDaoFileManager(virtualFile)
        fun getFileName(databaseName: String) = "${databaseName.toPascalCase()}$SUFFIX"
        fun createNewInstance(insertionPackage: DatabasePackage, entityNames: List<String>): DatabaseDaoFileManager? {
            val fileName = getFileName(insertionPackage.databaseName)
            return insertionPackage.createNewFile(
                fileName,
                DatabaseDaoTemplate(
                    packageManager = insertionPackage,
                    fileName = fileName,
                    entities = entityNames,
                ).createContent()
            )?.let { DatabaseDaoFileManager(it) }
        }
    }
}