package com.github.chrisjanusa.mvi.package_structure.manager.common.service.database.type_converter

import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticSuffixChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootChild
import com.intellij.openapi.vfs.VirtualFile

open class CommonTypeConverterFileManager(file: VirtualFile) : FileManager(file), RootChild {
    val typeConverterPackage by lazy {
        CommonTypeConverterPackage(file.parent)
    }
    val databasePackage by lazy {
        typeConverterPackage.databasePackage
    }
    val servicePackage by lazy {
        typeConverterPackage.servicePackage
    }
    val commonPackage by lazy {
        typeConverterPackage.commonPackage
    }
    override val rootPackage by lazy {
        typeConverterPackage.rootPackage
    }

    companion object : StaticSuffixChildInstanceCompanion("TypeConverter", CommonTypeConverterPackage) {
        override fun createInstance(virtualFile: VirtualFile) = CommonTypeConverterFileManager(virtualFile)
        fun getFileName(typeConverterName: String) = "$typeConverterName$SUFFIX"
        fun createNewInstance(insertionPackage: CommonTypeConverterPackage, typeConverterName: String, type: String): CommonTypeConverterFileManager? {
            val name = getFileName(typeConverterName)
            return insertionPackage.createNewFile(
                name,
                CommonTypeConverterTemplate(insertionPackage, name, type)
                    .createContent()
            )?.let { CommonTypeConverterFileManager(it) }
        }
    }
}