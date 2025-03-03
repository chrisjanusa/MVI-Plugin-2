package com.github.chrisjanusa.mvi.action.feature.service.mapper

import com.github.chrisjanusa.mvi.package_structure.manager.base.ModelFileManager

internal data class MapperPromptResult(
    var from: ModelFileManager? = null,
    var to: ModelFileManager? = null,
)
