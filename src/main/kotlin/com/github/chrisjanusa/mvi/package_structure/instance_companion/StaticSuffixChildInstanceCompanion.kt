package com.github.chrisjanusa.mvi.package_structure.instance_companion

abstract class StaticSuffixChildInstanceCompanion(suffix: String, vararg validParentCompanions: InstanceCompanion): StaticPatternChildInstanceCompanion(
    prefix = "",
    suffix = suffix,
    validParentCompanions = validParentCompanions
)