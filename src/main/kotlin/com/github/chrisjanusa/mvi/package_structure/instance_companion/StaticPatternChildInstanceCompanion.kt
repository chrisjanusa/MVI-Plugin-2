package com.github.chrisjanusa.mvi.package_structure.instance_companion

import com.intellij.openapi.vfs.VirtualFile

abstract class StaticPatternChildInstanceCompanion(prefix: String, suffix: String, private vararg val validParentCompanions: InstanceCompanion):
    InstanceCompanion() {
    val SUFFIX = suffix
    val PREFIX = prefix

    override fun isInstance(file: VirtualFile?): Boolean {
        return file.hasParent(validParentCompanions) && file.hasPattern(prefix = PREFIX, suffix = SUFFIX)
    }
}