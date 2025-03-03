package com.github.chrisjanusa.mvi.package_structure.manager.common.service.data

import com.github.chrisjanusa.mvi.package_structure.Manager
import com.github.chrisjanusa.mvi.package_structure.manager.base.Template

internal class CommonServiceDataErrorTemplate(
    packageManager: Manager,
    fileName: String,
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {
    override fun createContent(): String =
        "sealed interface DataError: Error {\n" +
        "    enum class Remote : DataError {\n" +
        "        REQUEST_TIME_OUT,\n" +
        "        TOO_MANY_REQUESTS,\n" +
        "        NO_INTERNET,\n" +
        "        SERVER_ERROR,\n" +
        "        SERIALIZATION,\n" +
        "        UNKNOWN\n" +
        "    }\n" +
        "\n" +
        "    enum class Local : DataError {\n" +
        "        DISK_FULL\n" +
        "    }\n" +
        "}\n"
}