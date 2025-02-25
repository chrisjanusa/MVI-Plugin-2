package com.github.chrisjanusa.mvi.package_structure.instance_companion

import com.intellij.openapi.vfs.VirtualFile

abstract class StaticInstanceCompanion(name: String): InstanceCompanion() {
    val NAME = name
    override fun isInstance(file: VirtualFile?): Boolean {
        return file.hasName(NAME)
    }
}