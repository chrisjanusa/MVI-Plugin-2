package com.github.chrisjanusa.mvi.package_structure.manager.base

import com.intellij.openapi.vfs.VirtualFile

abstract class ModelFileManager(modelFile: VirtualFile) : FileManager(modelFile) {
    fun getAllProperties(): Map<String, String> {
        val propertyLines = mutableListOf<String>()
        documentLines.forEach {
            if (it.contains("val ")) {
                propertyLines.add(it)
            }
        }
        return propertyLines.associate {
            val propertyName = it.substringAfter("val ").substringBefore(":")
            val propertyType = it.substringAfter(": ").substringBefore(",").substringBefore(" ")
            propertyName to propertyType
        }
    }

    abstract val modelName: String

    abstract val typeName: String
}