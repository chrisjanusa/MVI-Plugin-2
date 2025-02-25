package com.github.chrisjanusa.mvi.package_structure.manager.base

import com.intellij.openapi.vfs.VirtualFile

open class ViewModelFileManager(file: VirtualFile) : FileManager(file) {
    fun addEffect(effectName: String) {
        addAfterFirst("${effectName}Effect,") { line ->
            line.contains("val effectList")
        }
        writeToDisk()
    }

    fun removeEffect(effectName: String) {
        removeFirst { line ->
            line.contains(effectName)
        }
        writeToDisk()
    }

    companion object {
        const val SUFFIX = "ViewModel"
    }
}