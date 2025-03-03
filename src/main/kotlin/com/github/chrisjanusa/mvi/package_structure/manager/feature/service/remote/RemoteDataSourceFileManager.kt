package com.github.chrisjanusa.mvi.package_structure.manager.feature.service.remote

import com.github.chrisjanusa.mvi.helper.file_helper.toPascalCase
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticPatternChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.intellij.openapi.vfs.VirtualFile

class RemoteDataSourceFileManager(
    file: VirtualFile,
    featureRemoteFileManager: FeatureRemoteFileManager = FeatureRemoteFileManager(file)
) : FileManager(file), IFeatureRemoteFileManager by featureRemoteFileManager {

    companion object : StaticPatternChildInstanceCompanion(prefix = "Remote", suffix = "DataSource", FeatureRemotePackage) {
        override fun createInstance(virtualFile: VirtualFile) = RemoteDataSourceFileManager(virtualFile)
        fun getFileName(dataSourceName: String) = "$PREFIX${dataSourceName.toPascalCase()}$SUFFIX"
        fun createNewInstance(insertionPackage: FeatureRemotePackage, baseUrl: String, endpoint: String): RemoteDataSourceFileManager? {
            val fileName = getFileName(insertionPackage.dataSourceName)
            return insertionPackage.createNewFile(
                fileName,
                RemoteDataSourceTemplate(
                    packageManager = insertionPackage,
                    fileName = fileName,
                    baseUrl = baseUrl,
                    endpoint = endpoint,
                    dataSourceName = insertionPackage.dataSourceName.toPascalCase()
                ).createContent()
            )?.let { RemoteDataSourceFileManager(it) }
        }
    }
}