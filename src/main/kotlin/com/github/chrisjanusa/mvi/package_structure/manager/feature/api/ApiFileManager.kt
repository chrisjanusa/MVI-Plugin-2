package com.github.chrisjanusa.mvi.package_structure.manager.feature.api

import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticPatternChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.parent_provider.FeatureChild
import com.intellij.openapi.vfs.VirtualFile

class ApiFileManager(file: VirtualFile) : FileManager(file), FeatureChild {
    val apiPackage: ApiPackage by lazy {
        ApiPackage(file.parent)
    }
    override val featurePackage by lazy {
        apiPackage.featurePackage
    }
    val featureName by lazy {
        apiPackage.featureName
    }
    override val rootPackage by lazy {
        apiPackage.rootPackage
    }

    val repositoryName by lazy {
        name.substringAfter(PREFIX).substringBefore(SUFFIX)
    }

    companion object : StaticPatternChildInstanceCompanion(prefix = "I", suffix = "Repository", ApiPackage) {
        override fun createInstance(virtualFile: VirtualFile) = ApiFileManager(virtualFile)
        fun getFileName(repositoryName: String) = "$PREFIX$repositoryName$SUFFIX"
        fun createNewInstance(insertionPackage: ApiPackage, repositoryName: String): ApiFileManager? {
            val fileName = getFileName(repositoryName)
            return insertionPackage.createNewFile(
                fileName,
                ApiTemplate(insertionPackage, fileName)
                    .createContent()
            )?.let { ApiFileManager(it) }
        }
    }
}