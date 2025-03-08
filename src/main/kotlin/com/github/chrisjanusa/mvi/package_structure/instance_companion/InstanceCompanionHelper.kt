package com.github.chrisjanusa.mvi.package_structure.instance_companion

import com.intellij.openapi.vfs.VirtualFile

internal fun VirtualFile?.hasPattern(prefix: String, suffix: String): Boolean {
    val fileName = this?.nameWithoutExtension ?: return false
    return fileName.startsWith(prefix) && fileName.endsWith(suffix)
}

internal fun VirtualFile?.hasName(name: String) =
    this?.nameWithoutExtension == name

internal fun VirtualFile?.hasChild(validChildrenCompanions: Array<out InstanceCompanion>) =
    this?.children?.any { child ->
        validChildrenCompanions.any { validChild ->
            validChild.isInstance(child)
        }
    } ?: false

internal fun VirtualFile?.hasParent(validParentCompanions: Array<out InstanceCompanion>) =
    this?.parent?.let { parent ->
        validParentCompanions.any { validParent ->
            validParent.isInstance(parent)
        }
    } ?: false