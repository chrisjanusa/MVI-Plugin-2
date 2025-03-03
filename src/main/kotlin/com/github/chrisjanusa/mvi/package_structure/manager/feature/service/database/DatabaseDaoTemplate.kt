package com.github.chrisjanusa.mvi.package_structure.manager.feature.service.database

import com.github.chrisjanusa.mvi.helper.file_helper.toCamelCase
import com.github.chrisjanusa.mvi.package_structure.Manager
import com.github.chrisjanusa.mvi.package_structure.manager.base.Template

internal class DatabaseDaoTemplate(
    packageManager: Manager,
    fileName: String,
    private val entities: List<String>
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {

    override fun createContent(): String {
        val entityName = entities.firstOrNull() ?: "Example"
        val exampleEntity = DatabaseEntityFileManager.getFileName(entityName)
        return "import androidx.room.Dao\n" +
        "import androidx.room.Delete\n" +
        "import androidx.room.Query\n" +
        "import androidx.room.Upsert\n" +
        "import kotlinx.coroutines.flow.Flow\n" +
        "\n" +
        "@Dao\n" +
        "interface $fileName {\n" +
        "    // Insert an element or update an existing one if already present\n" +
        "    @Upsert\n" +
        "    suspend fun upsert(${entityName.toCamelCase()}: $exampleEntity)\n" +
        "\n" +
        "    // Get a flow of lists that will emit new elements as they are added\n" +
        "    @Query(\"SELECT * FROM $exampleEntity\")\n" +
        "    fun get${entityName}s(): Flow<List<$exampleEntity>>\n" +
        "\n" +
        "    // Retrieve element with id matching parameter\n" +
        "    @Query(\"SELECT * FROM $exampleEntity WHERE id = :id\")\n" +
        "    suspend fun get$entityName(id: String): $exampleEntity?\n" +
        "\n" +
        "    // Delete entity from database\n" +
        "    @Delete\n" +
        "    suspend fun delete$entityName(book: $exampleEntity)\n" +
        "}"
    }
}