package com.github.chrisjanusa.mvi.package_structure.parent_provider

import com.github.chrisjanusa.mvi.package_structure.manager.common.CommonPackage

interface CommonChild: RootChild {
    val commonPackage: CommonPackage
}