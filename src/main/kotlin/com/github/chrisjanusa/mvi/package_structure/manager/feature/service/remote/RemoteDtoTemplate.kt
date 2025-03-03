package com.github.chrisjanusa.mvi.package_structure.manager.feature.service.remote

import com.github.chrisjanusa.mvi.package_structure.Manager
import com.github.chrisjanusa.mvi.package_structure.manager.base.Template

internal class RemoteDtoTemplate(
    packageManager: Manager,
    fileName: String,
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {

    override fun createContent(): String =
        "import kotlinx.serialization.SerialName\n" +
        "import kotlinx.serialization.Serializable\n" +
        "\n" +
        "@Serializable\n" +
        "data class $fileName(\n" +
        "    @SerialName(\"key\") val id: String,\n" +
        ")\n"
}