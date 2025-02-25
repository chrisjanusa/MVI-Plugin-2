package com.github.chrisjanusa.mvi.package_structure.manager.feature.shared.helper

import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticSuffixChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.feature.shared.generated.SharedGeneratedPackage
import com.github.chrisjanusa.mvi.package_structure.manager.feature.shared.SharedPackage

abstract class SharedFileNameProvider(suffix: String): StaticSuffixChildInstanceCompanion(
    suffix = suffix,
    SharedPackage,
    SharedGeneratedPackage
) {
    fun getFileName(featureName: String): String = "${featureName}$SHARED_SUFFIX$SUFFIX"

    companion object {
        const val SHARED_SUFFIX = "Shared"
    }
}