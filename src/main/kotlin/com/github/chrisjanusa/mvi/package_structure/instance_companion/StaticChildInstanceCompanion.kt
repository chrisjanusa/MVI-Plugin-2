package com.github.chrisjanusa.mvi.package_structure.instance_companion

import com.intellij.openapi.vfs.VirtualFile

abstract class StaticChildInstanceCompanion(name: String, private vararg val validParentCompanions: InstanceCompanion) :
    InstanceCompanion() {
    val NAME = name

    override fun isInstance(file: VirtualFile?): Boolean {
        return file.hasParent(validParentCompanions) && file.hasName(NAME)
    }
}