package com.github.chrisjanusa.mvi.package_structure.manager.feature.shared.helper

import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.shared.generated.SharedGeneratedPackage
import com.github.chrisjanusa.mvi.package_structure.manager.feature.shared.SharedPackage
import com.intellij.openapi.vfs.VirtualFile

class SharedFileManager(file: VirtualFile) : ISharedFileManager, FileManager(file) {
    private val isGenerated = file.parent.name == SharedGeneratedPackage.NAME
    override val generatedPackage: SharedGeneratedPackage? by lazy {
        if (isGenerated) SharedGeneratedPackage(file.parent) else sharedPackage.generatedPackage
    }
    override val sharedPackage: SharedPackage by lazy {
        if (isGenerated) {
            generatedPackage?.sharedPackage ?: SharedPackage(file.parent)
        } else {
            SharedPackage(file.parent)
        }
    }
    override val featurePackage by lazy {
        sharedPackage.featurePackage
    }
    override val featureName by lazy {
        sharedPackage.featureName
    }
    override val rootPackage by lazy {
        sharedPackage.rootPackage
    }
}