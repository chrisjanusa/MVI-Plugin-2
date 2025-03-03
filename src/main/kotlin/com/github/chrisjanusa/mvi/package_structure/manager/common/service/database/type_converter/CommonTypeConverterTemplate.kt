package com.github.chrisjanusa.mvi.package_structure.manager.common.service.database.type_converter

import com.github.chrisjanusa.mvi.package_structure.Manager
import com.github.chrisjanusa.mvi.package_structure.manager.base.Template

internal class CommonTypeConverterTemplate(
    packageManager: Manager,
    fileName: String,
    private val type: String
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {
    override fun createContent(): String =
        "import androidx.room.TypeConverter\n" +
        "import kotlinx.serialization.encodeToString\n" +
        "import kotlinx.serialization.json.Json\n" +
        "\n" +
        "object $fileName {\n" +
        "    @TypeConverter\n" +
        "    fun fromString(value: String): $type {\n" +
        "        return Json.decodeFromString(value)\n" +
        "    }\n" +
        "\n" +
        "    @TypeConverter\n" +
        "    fun toString(value: $type): String {\n" +
        "        return Json.encodeToString(value)\n" +
        "    }\n" +
        "}"
}