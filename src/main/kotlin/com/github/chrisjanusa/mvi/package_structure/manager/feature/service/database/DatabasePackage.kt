package com.github.chrisjanusa.mvi.package_structure.manager.feature.service.database

import com.github.chrisjanusa.mvi.helper.file_helper.findChildFile
import com.github.chrisjanusa.mvi.helper.file_helper.toPascalCase
import com.github.chrisjanusa.mvi.package_structure.instance_companion.ChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.parent_provider.ServiceChild
import com.intellij.openapi.vfs.VirtualFile

class DatabasePackage(file: VirtualFile): PackageManager(file), ServiceChild {
    val databaseWrapperPackage by lazy {
        DatabaseWrapperPackage(file.parent)
    }
    override val servicePackage by lazy {
        databaseWrapperPackage.servicePackage
    }
    val featureName by lazy {
        databaseWrapperPackage.featureName
    }
    override val featurePackage by lazy {
        databaseWrapperPackage.featurePackage
    }
    override val rootPackage by lazy {
        databaseWrapperPackage.rootPackage
    }
    val databaseName by lazy {
        name.toPascalCase()
    }
    val database by lazy {
        file.findChildFile(DatabaseClassFileManager.getFileName(databaseName))?.let { DatabaseClassFileManager(it) }
    }
    val dao by lazy {
        file.findChildFile(DatabaseDaoFileManager.getFileName(databaseName))?.let { DatabaseDaoFileManager(it) }
    }
    val entities by lazy {
        file.children.mapNotNull {
            if (DatabaseEntityFileManager.isInstance(it)) {
                DatabaseEntityFileManager(it)
            } else {
                null
            }
        }
    }

    private fun createAllChildren(entityNames: List<String>) {
        DatabaseClassFileManager.createNewInstance(this, entityNames)
        DatabaseDaoFileManager.createNewInstance(this, entityNames)
        entityNames.forEach {
            createEntity(it)
        }
    }

    fun createEntity(entityName: String): DatabaseEntityFileManager? {
        return DatabaseEntityFileManager.createNewInstance(this, entityName)
    }

    companion object : ChildInstanceCompanion(DatabaseWrapperPackage) {
        override fun createInstance(virtualFile: VirtualFile) = DatabasePackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(
                DatabaseClassFileManager,
                DatabaseDaoFileManager,
                DatabaseEntityFileManager
            )

        fun createNewInstance(
            insertionPackage: DatabaseWrapperPackage,
            databaseName: String,
            entityNames: List<String>,
        ): DatabasePackage? {
            val packageManager = insertionPackage.createNewDirectory(databaseName)?.let { DatabasePackage(it) }
            packageManager?.createAllChildren(entityNames)
            return packageManager
        }
    }
}