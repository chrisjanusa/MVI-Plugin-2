package com.github.chrisjanusa.mvi.package_structure.manager.feature.service.database

import com.github.chrisjanusa.mvi.helper.file_helper.toCamelCase
import com.github.chrisjanusa.mvi.package_structure.Manager
import com.github.chrisjanusa.mvi.package_structure.manager.base.Template

internal class DatabaseClassTemplate(
    packageManager: Manager,
    fileName: String,
    private val entities: List<String>
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {

    override fun createContent(): String {
        val databaseName = fileName.substringBefore(DatabaseClassFileManager.SUFFIX)
        return "import androidx.room.Database\n" +
        "import androidx.room.RoomDatabase\n" +
        "\n" +
        "@Database(\n" +
        "    entities = [\n" +
        entities.joinToString(separator = "\n") {
        "        ${it.toEntityFileName()}::class,"
        } +
        "    ],\n" +
        "    version = 1\n" +
        ")\n" +
        "abstract class $fileName: RoomDatabase() {\n" +
        "    abstract val ${databaseName.toCamelCase()}Dao: ${databaseName}Dao\n" +
        "\n" +
        "    companion object {\n" +
        "        const val DB_NAME = \"${databaseName}.db\"\n" +
        "    }\n" +
        "}"
    }

    private fun String.toEntityFileName() = DatabaseEntityFileManager.getFileName(this)
}