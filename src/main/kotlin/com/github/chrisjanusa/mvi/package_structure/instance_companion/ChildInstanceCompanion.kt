package com.github.chrisjanusa.mvi.package_structure.instance_companion

import com.intellij.openapi.vfs.VirtualFile

 abstract class ChildInstanceCompanion(private vararg val validParentCompanions: InstanceCompanion):
     InstanceCompanion() {
    override fun isInstance(file: VirtualFile?): Boolean {
        return file.hasParent(validParentCompanions)
    }
}