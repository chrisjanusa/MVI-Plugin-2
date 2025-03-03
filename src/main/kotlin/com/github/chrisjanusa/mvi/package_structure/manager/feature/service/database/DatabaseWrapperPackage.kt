package com.github.chrisjanusa.mvi.package_structure.manager.feature.service.database

import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.service.ServicePackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.ServiceChild
import com.intellij.openapi.vfs.VirtualFile

class DatabaseWrapperPackage(file: VirtualFile): PackageManager(file), ServiceChild {
    val databases by lazy {
        file.children.map { DatabasePackage(it) }
    }
    val allEntities by lazy {
        databases.flatMap { it.entities }
    }
    override val servicePackage by lazy {
        ServicePackage(file.parent)
    }
    val featureName by lazy {
        servicePackage.featureName
    }
    override val featurePackage by lazy {
        servicePackage.featurePackage
    }
    override val rootPackage by lazy {
        servicePackage.rootPackage
    }

    fun createDatabase(databaseName: String, entityNames: List<String>): DatabasePackage? {
        return DatabasePackage.createNewInstance(
            insertionPackage = this,
            databaseName = databaseName,
            entityNames = entityNames
        )
    }

    companion object : StaticInstanceCompanion("database") {
        override fun createInstance(virtualFile: VirtualFile) = DatabaseWrapperPackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(DatabasePackage)

        fun createNewInstance(insertionPackage: ServicePackage): DatabaseWrapperPackage? {
            return insertionPackage.createNewDirectory(NAME)?.let { DatabaseWrapperPackage(it) }
        }
    }
}