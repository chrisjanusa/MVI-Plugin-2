package com.github.chrisjanusa.mvi.package_structure.manager.common.service

import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.common.CommonPackage
import com.github.chrisjanusa.mvi.package_structure.manager.common.service.data.CommonDataPackage
import com.github.chrisjanusa.mvi.package_structure.manager.common.service.database.CommonDatabasePackage
import com.github.chrisjanusa.mvi.package_structure.manager.common.service.remote.CommonRemotePackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootChild
import com.intellij.openapi.vfs.VirtualFile

class CommonServicePackage(file: VirtualFile) : PackageManager(file), RootChild {
    val commonPackage by lazy {
        CommonPackage(file.parent)
    }
    override val rootPackage by lazy {
        commonPackage.rootPackage
    }
    val databasePackage by lazy {
        file.findChild(CommonDatabasePackage.NAME)?.let { CommonDatabasePackage(it) }
    }
    val remotePackage by lazy {
        file.findChild(CommonRemotePackage.NAME)?.let { CommonRemotePackage(it) }
    }
    val dataPackage by lazy {
        file.findChild(CommonDataPackage.NAME)?.let { CommonDataPackage(it) }
    }
    val typeConverterPackage by lazy {
        databasePackage?.typeConverterPackage
    }
    val typeConverters by lazy {
        databasePackage?.typeConverters
    }

    fun createTypeConverter(typeConverterName: String, type: String) {
        val databasePackageInstance = databasePackage ?: CommonDatabasePackage.createNewInstance(this)
        databasePackageInstance?.createTypeConverter(typeConverterName, type)
    }

    fun createRemoteHelpers() =
        remotePackage ?: CommonRemotePackage.createNewInstance(this)

    fun createData() =
        dataPackage ?: CommonDataPackage.createNewInstance(this)

    companion object : StaticChildInstanceCompanion("service", CommonPackage) {
        override fun createInstance(virtualFile: VirtualFile) = CommonServicePackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(CommonDatabasePackage, CommonDataPackage, CommonRemotePackage)
        fun createNewInstance(insertionPackage: CommonPackage): CommonServicePackage? {
            val commonPackage = insertionPackage.createNewDirectory(NAME)?.let { CommonServicePackage(it) }
            return commonPackage
        }
    }
}