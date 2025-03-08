package com.github.chrisjanusa.mvi.package_structure.instance_companion

import com.intellij.openapi.vfs.VirtualFile

abstract class StaticExcludeChildInstanceCompanion(name: String, private vararg val invalidParentCompanions: InstanceCompanion) :
    InstanceCompanion() {
    val NAME = name

    override fun isInstance(file: VirtualFile?): Boolean {
        return !file.hasParent(invalidParentCompanions) && file.hasName(NAME)
    }
}