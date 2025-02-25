package com.github.chrisjanusa.mvi.package_structure.instance_companion

import com.intellij.openapi.vfs.VirtualFile

abstract class ParentInstanceCompanion(private vararg val validChildrenCompanions: InstanceCompanion):
    InstanceCompanion() {
    override fun isInstance(file: VirtualFile?): Boolean {
        return file.hasChild(validChildrenCompanions)
    }
}