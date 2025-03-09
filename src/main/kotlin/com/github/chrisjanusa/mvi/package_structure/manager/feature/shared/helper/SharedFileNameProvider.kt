package com.github.chrisjanusa.mvi.package_structure.manager.feature.shared.helper

import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticSuffixChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.feature.shared.SharedPackage
import com.github.chrisjanusa.mvi.package_structure.manager.feature.shared.generated.SharedGeneratedPackage

abstract class SharedFileNameProvider(suffix: String): StaticSuffixChildInstanceCompanion(
    suffix = "$SHARED_SUFFIX$suffix",
    SharedPackage,
    SharedGeneratedPackage
) {
    fun getFileName(featureName: String): String = "${featureName}$SUFFIX"

    companion object {
        const val SHARED_SUFFIX = "Shared"
    }
}