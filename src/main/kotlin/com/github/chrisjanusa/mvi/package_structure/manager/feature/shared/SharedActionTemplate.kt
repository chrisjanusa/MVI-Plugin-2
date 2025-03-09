package com.github.chrisjanusa.mvi.package_structure.manager.feature.shared

import com.github.chrisjanusa.mvi.package_structure.Manager
import com.github.chrisjanusa.mvi.package_structure.manager.base.Template
import com.github.chrisjanusa.mvi.package_structure.manager.foundation.FoundationSliceFileManager

internal class SharedActionTemplate(
    packageManager: Manager,
    fileName: String
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {

    override fun createContent(): String =
        "import ${rootPackage?.foundationPackage?.action?.packagePathExcludingFile}.ReducibleAction\n" +
        "import ${foundationPackage?.slice?.noSlicePackagePath}\n" +
        "\n" +
        "internal sealed class $fileName : ReducibleAction<${featureName}SharedState, ${FoundationSliceFileManager.NO_SLICE}> {\n" +
        "}\n"
}