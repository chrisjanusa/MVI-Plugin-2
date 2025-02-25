package com.github.chrisjanusa.mvi.package_structure.instance_companion

import com.intellij.openapi.vfs.VirtualFile

abstract class StaticPatternInstanceCompanion(prefix: String, suffix: String): InstanceCompanion() {
    val PREFIX = prefix
    val SUFFIX = suffix
    override fun isInstance(file: VirtualFile?): Boolean {
        return file.hasPattern(prefix = PREFIX, suffix = SUFFIX)
    }
}