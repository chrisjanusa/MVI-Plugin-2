package com.github.chrisjanusa.mvi.package_structure.manager.common.service.database

import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.common.service.CommonServicePackage
import com.github.chrisjanusa.mvi.package_structure.manager.common.service.database.type_converter.CommonTypeConverterPackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.CommonChild
import com.intellij.openapi.vfs.VirtualFile

class CommonDatabasePackage(file: VirtualFile) : PackageManager(file), CommonChild {
    val servicePackage by lazy {
        CommonServicePackage(file.parent)
    }
    override val commonPackage by lazy {
        servicePackage.commonPackage
    }
    override val rootPackage by lazy {
        servicePackage.rootPackage
    }
    val typeConverterPackage by lazy {
        file.findChild(CommonTypeConverterPackage.NAME)?.let { CommonTypeConverterPackage(it) }
    }
    val typeConverters by lazy {
        typeConverterPackage?.typeConverters
    }

    fun createTypeConverter(typeConverterName: String, type: String) {
        val typeConverterPackageInstance = typeConverterPackage ?: CommonTypeConverterPackage.createNewInstance(this)
        typeConverterPackageInstance?.createTypeConverter(typeConverterName, type)
    }

    companion object : StaticChildInstanceCompanion("database", CommonServicePackage) {
        override fun createInstance(virtualFile: VirtualFile) = CommonDatabasePackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(CommonTypeConverterPackage)
        fun createNewInstance(insertionPackage: CommonServicePackage): CommonDatabasePackage? {
            val commonPackage = insertionPackage.createNewDirectory(NAME)?.let { CommonDatabasePackage(it) }
            return commonPackage
        }
    }
}