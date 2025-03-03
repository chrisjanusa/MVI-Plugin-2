package com.github.chrisjanusa.mvi.package_structure.manager.base

import com.intellij.openapi.vfs.VirtualFile

abstract class ModelFileManager(modelFile: VirtualFile) : FileManager(modelFile) {
    fun getAllProperties(): Map<String, String> {
        var withinClassProperties = false
        val propertyLines = mutableListOf<String>()
        documentLines.forEach {
            if (it.contains(")")) {
                withinClassProperties = false
            }
            if (withinClassProperties) {
                propertyLines.add(it)
            }
            if (it.contains("data class")) {
                withinClassProperties = true
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