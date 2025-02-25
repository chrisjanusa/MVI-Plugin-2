package com.github.chrisjanusa.mvi.package_structure.manager.app.helper

import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticSuffixChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.app.AppPackage

abstract class AppFileNameProvider(suffix: String) : StaticSuffixChildInstanceCompanion(
    suffix,
    AppPackage
) {
    fun getFileName(appName: String): String = "$appName$SUFFIX"
}