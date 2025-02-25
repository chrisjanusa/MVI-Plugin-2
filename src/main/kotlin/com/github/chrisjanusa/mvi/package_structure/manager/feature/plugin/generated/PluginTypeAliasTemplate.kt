package com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.generated

import com.github.chrisjanusa.mvi.package_structure.Manager
import com.github.chrisjanusa.mvi.package_structure.manager.base.Template
import com.github.chrisjanusa.mvi.package_structure.manager.base.addIf
import com.github.chrisjanusa.mvi.package_structure.manager.foundation.FoundationSliceFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.foundation.FoundationStateFileManager

internal class PluginTypeAliasTemplate(
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
        return "import $rootPackagePath.foundation.ActionEffect\n" +
                "import $rootPackagePath.foundation.Effect\n" +
                "import $rootPackagePath.foundation.NavEffect\n" +
                "import $rootPackagePath.foundation.StateEffect\n" +
                "import $rootPackagePath.foundation.StateSliceEffect\n" +
                "import ${pluginPackage?.slice?.packagePath}\n".addIf { hasSlice } +
                "import ${foundationPackage?.slice?.packagePathExcludingFile}.NoSlice\n".addIf { !hasSlice } +
                "import ${pluginPackage?.state?.packagePath}\n".addIf { hasState } +
                "import ${foundationPackage?.state?.packagePathExcludingFile}.NoState\n".addIf { !hasState } +
                "\n" +
                "internal abstract class ${pluginName}NavEffect: NavEffect<$state, $slice>()\n" +
                "\n" +
                "internal typealias ${pluginName}Effect = Effect<$state, $slice>\n" +
                "\n" +
                "internal typealias ${pluginName}StateEffect = StateEffect<$state, $slice>\n" +
                "\n" +
                "internal typealias ${pluginName}StateSliceEffect = StateSliceEffect<$state, $slice>\n" +
                "\n" +
                "internal typealias ${pluginName}ActionEffect = ActionEffect<$state, $slice>\n"
    }
}