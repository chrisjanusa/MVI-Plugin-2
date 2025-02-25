package com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin

import com.github.chrisjanusa.mvi.package_structure.Manager
import com.github.chrisjanusa.mvi.package_structure.manager.base.Template
import com.github.chrisjanusa.mvi.package_structure.manager.base.addIf
import com.github.chrisjanusa.mvi.package_structure.manager.foundation.FoundationSliceFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.foundation.FoundationStateFileManager

internal class PluginActionTemplate(
    private val hasState: Boolean,
    private val hasSlice: Boolean,
    packageManager: Manager,
    fileName: String
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {

    override fun createContent(): String {
        val state = if (hasState) { pluginPackage?.state?.name ?: "plugin not found" } else FoundationStateFileManager.NO_STATE
        val slice = if (hasSlice) { pluginPackage?.slice?.name ?: "plugin not found" } else FoundationSliceFileManager.NO_SLICE
        return "import ${foundationPackage?.action?.packagePathExcludingFile}.ReducibleAction\n" +
                "import ${foundationPackage?.slice?.noSlicePackagePath}\n".addIf { !hasSlice } +
                "import ${foundationPackage?.state?.noStatePackagePath}\n".addIf { !hasState } +
                "\n" +
                "\n" +
                "sealed class $fileName : ReducibleAction<$state, $slice> {\n" +
                "}\n"
    }
}