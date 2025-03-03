package com.github.chrisjanusa.mvi.package_structure.manager.feature.service.database

import com.github.chrisjanusa.mvi.package_structure.Manager
import com.github.chrisjanusa.mvi.package_structure.manager.base.Template

internal class DatabaseEntityTemplate(
    packageManager: Manager,
    fileName: String,
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {

    override fun createContent(): String {
        return "import androidx.room.Entity\n" +
        "import androidx.room.PrimaryKey\n" +
        "\n" +
        "@Entity\n" +
        "// Composite Key Example: @Entity(primaryKeys = [\"firstName\", \"lastName\"])" +
        "data class $fileName(\n" +
        "    @PrimaryKey(autoGenerate = false) val id: String,\n" +
        ")\n"
    }
}