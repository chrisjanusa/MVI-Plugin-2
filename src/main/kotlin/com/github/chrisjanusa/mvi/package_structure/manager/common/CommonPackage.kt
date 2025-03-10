package com.github.chrisjanusa.mvi.package_structure.manager.common

import com.github.chrisjanusa.mvi.helper.file_helper.toPascalCase
import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.RootPackage
import com.github.chrisjanusa.mvi.package_structure.manager.common.helper.CommonHelperPackage
import com.github.chrisjanusa.mvi.package_structure.manager.common.nav.CommonNavPackage
import com.github.chrisjanusa.mvi.package_structure.manager.common.service.CommonServicePackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootDirectChild
import com.intellij.openapi.vfs.VirtualFile

class CommonPackage(file: VirtualFile) : PackageManager(file), RootDirectChild {
    val featureName = name.toPascalCase()
    override val rootPackage by lazy {
        RootPackage(file.parent)
    }

    val navPackage by lazy {
        file.findChild(CommonNavPackage.NAME)?.let { CommonNavPackage(it) }
    }
    val coreNavAction by lazy {
        navPackage?.coreNavAction
    }

    val helperPackage by lazy {
        file.findChild(CommonHelperPackage.NAME)?.let { CommonHelperPackage(it) }
    }
    val classNameHelper by lazy {
        helperPackage?.classNameHelper
    }
    val servicePackage by lazy {
        file.findChild(CommonServicePackage.NAME)?.let { CommonServicePackage(it) }
    }
    val databasePackage by lazy {
        servicePackage?.databasePackage
    }
    val typeConverterPackage by lazy {
        servicePackage?.typeConverterPackage
    }
    val typeConverters by lazy {
        servicePackage?.typeConverters
    }

    fun createServicePackage(): CommonServicePackage? {
        return file.findChild(CommonServicePackage.NAME)?.let { CommonServicePackage(it) } ?: CommonServicePackage.createNewInstance(this)
    }

    fun createTypeConverter(typeConverterName: String, type: String) {
        val servicePackage = createServicePackage()
        servicePackage?.createTypeConverter(typeConverterName, type)
    }

    private fun createAllChildren() {
        CommonHelperPackage.createNewInstance(this)
        CommonNavPackage.createNewInstance(this)
    }

    companion object : StaticChildInstanceCompanion("common", RootPackage) {
        override fun createInstance(virtualFile: VirtualFile) = CommonPackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(CommonHelperPackage, CommonNavPackage)
        fun createNewInstance(insertionPackage: RootPackage): CommonPackage? {
            val commonPackage = insertionPackage.createNewDirectory(NAME)?.let { CommonPackage(it) }
            commonPackage?.createAllChildren()
            return commonPackage
        }
    }
}