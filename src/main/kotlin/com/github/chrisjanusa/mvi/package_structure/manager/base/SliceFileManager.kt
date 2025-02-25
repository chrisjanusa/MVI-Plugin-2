package com.github.chrisjanusa.mvi.package_structure.manager.base

import com.intellij.openapi.vfs.VirtualFile

open class SliceFileManager(file: VirtualFile) : FileManager(file) {
    companion object {
        const val SUFFIX = "Slice"
    }
}