package com.github.chrisjanusa.mvi.package_structure.manager.feature.service.database

import com.github.chrisjanusa.mvi.helper.file_helper.toCamelCase
import com.github.chrisjanusa.mvi.helper.file_helper.toPascalCase
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticSuffixChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.intellij.openapi.vfs.VirtualFile

class DatabaseClassFileManager(
    file: VirtualFile,
    featureDatabaseFileManager: FeatureDatabaseFileManager = FeatureDatabaseFileManager(file)
) : FileManager(file), IFeatureDatabaseFileManager by featureDatabaseFileManager {

    val daoInstance by lazy {
        DatabaseDaoFileManager.getFileName(databaseName).toCamelCase()
    }

    companion object : StaticSuffixChildInstanceCompanion("Database", DatabasePackage) {
        override fun createInstance(virtualFile: VirtualFile) = DatabaseClassFileManager(virtualFile)
        fun getFileName(databaseName: String) = "${databaseName.toPascalCase()}$SUFFIX"
        fun createNewInstance(insertionPackage: DatabasePackage, entityNames: List<String>): DatabaseClassFileManager? {
            val fileName = getFileName(insertionPackage.databaseName)
            return insertionPackage.createNewFile(
                fileName,
                DatabaseClassTemplate(
                    packageManager = insertionPackage,
                    fileName = fileName,
                    entities = entityNames,
                ).createContent()
            )?.let { DatabaseClassFileManager(it) }
        }
    }
}