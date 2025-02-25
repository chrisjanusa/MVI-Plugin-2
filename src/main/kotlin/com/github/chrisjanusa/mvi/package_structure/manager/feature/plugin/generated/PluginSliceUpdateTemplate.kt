package com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.generated

import com.github.chrisjanusa.mvi.package_structure.Manager
import com.github.chrisjanusa.mvi.package_structure.manager.base.Template

internal class PluginSliceUpdateTemplate(
    packageManager: Manager,
    fileName: String
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {

    override fun createContent(): String =
                "import ${foundationPackage?.sliceUpdate?.packagePath}\n" +
                "import ${pluginPackage?.slice?.packagePath}\n" +
                "\n" +
                "data class ${fileName}(\n" +
                "    val slice: ${pluginPackage?.slice}\n" +
                ") : ${foundationPackage?.sliceUpdate}\n"
}