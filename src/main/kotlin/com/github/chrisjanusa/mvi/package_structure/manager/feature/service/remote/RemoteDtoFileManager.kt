package com.github.chrisjanusa.mvi.package_structure.manager.feature.service.remote

import com.github.chrisjanusa.mvi.helper.file_helper.toPascalCase
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticSuffixChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.base.ModelFileManager
import com.intellij.openapi.vfs.VirtualFile

class RemoteDtoFileManager(
    file: VirtualFile,
    featureRemoteFileManager: FeatureRemoteFileManager = FeatureRemoteFileManager(file)
) : ModelFileManager(file), IFeatureRemoteFileManager by featureRemoteFileManager {

    override val modelName: String
        get() = name.substringBefore(SUFFIX)
    override val typeName: String
        get() = SUFFIX

    companion object : StaticSuffixChildInstanceCompanion(suffix = "DTO", FeatureRemotePackage) {
        override fun createInstance(virtualFile: VirtualFile) = RemoteDtoFileManager(virtualFile)
        fun getFileName(dataSourceName: String) = "${dataSourceName.toPascalCase()}$SUFFIX"
        fun createNewInstance(insertionPackage: FeatureRemotePackage, dtoName: String): RemoteDtoFileManager? {
            val fileName = getFileName(dtoName)
            return insertionPackage.createNewFile(
                fileName,
                RemoteDtoTemplate(
                    packageManager = insertionPackage,
                    fileName = fileName,
                ).createContent()
            )?.let { RemoteDtoFileManager(it) }
        }
    }
}