package com.github.chrisjanusa.mvi.package_structure.manager.common.service.database.type_converter

import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.common.service.database.CommonDatabasePackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootChild
import com.intellij.openapi.vfs.VirtualFile

class CommonTypeConverterPackage(file: VirtualFile) : PackageManager(file), RootChild {
    val databasePackage by lazy {
        CommonDatabasePackage(file.parent)
    }
    val servicePackage by lazy {
        databasePackage.servicePackage
    }
    val commonPackage by lazy {
        databasePackage.commonPackage
    }
    override val rootPackage by lazy {
        databasePackage.rootPackage
    }
    val typeConverters by lazy {
        file.children.map {
            CommonTypeConverterFileManager(it)
        }
    }

    fun createTypeConverter(typeConverterName: String, type: String) {
        CommonTypeConverterFileManager.createNewInstance(this, typeConverterName, type)
    }

    companion object : StaticChildInstanceCompanion("type_converter", CommonDatabasePackage) {
        override fun createInstance(virtualFile: VirtualFile) = CommonTypeConverterPackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(CommonTypeConverterFileManager)
        fun createNewInstance(insertionPackage: CommonDatabasePackage): CommonTypeConverterPackage? {
            val commonPackage = insertionPackage.createNewDirectory(NAME)?.let { CommonTypeConverterPackage(it) }
            return commonPackage
        }
    }
}