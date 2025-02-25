package com.github.chrisjanusa.mvi.package_structure.manager.feature.domain_model

import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.instance_companion.ChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.parent_provider.FeatureChild
import com.intellij.openapi.vfs.VirtualFile

class DomainModelFileManager(actionFile: VirtualFile) : FileManager(actionFile), FeatureChild {
    val domainModelPackage: DomainModelPackage by lazy {
        DomainModelPackage(file.parent)
    }
    override val featurePackage by lazy {
        domainModelPackage.featurePackage
    }
    val featureName by lazy {
        domainModelPackage.featureName
    }
    override val rootPackage by lazy {
        domainModelPackage.rootPackage
    }

    companion object : ChildInstanceCompanion(DomainModelPackage) {
        override fun createInstance(virtualFile: VirtualFile) = DomainModelFileManager(virtualFile)
        fun createNewInstance(insertionPackage: DomainModelPackage, modelName: String): DomainModelFileManager? {
            return insertionPackage.createNewFile(
                modelName,
                DomainModelTemplate(insertionPackage, modelName)
                    .createContent()
            )?.let { DomainModelFileManager(it) }
        }
    }
}