package com.github.chrisjanusa.mvi.package_structure.manager.base

import com.intellij.openapi.vfs.VirtualFile

open class ViewModelFileManager(file: VirtualFile) : FileManager(file) {
    fun addEffect(effectName: String, import: String?) {
        addAfterFirst("        ${effectName}${EffectFileManager.SUFFIX},") { line ->
            line.contains("val effectList")
        }
        if (import != null) {
            addImport(import)
        }
        writeToDisk()
    }

    fun removeEffect(effectName: String) {
        removeAllLinesThatMatch { line ->
            line.contains(effectName)
        }
        writeToDisk()
    }

    companion object {
        const val SUFFIX = "ViewModel"
    }
}