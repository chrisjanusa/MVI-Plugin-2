package com.github.chrisjanusa.mvi.package_structure.parent_provider

import com.github.chrisjanusa.mvi.package_structure.manager.feature.service.database.DatabasePackage

interface DatabaseChild: ServiceChild {
    val databasePackage: DatabasePackage
}