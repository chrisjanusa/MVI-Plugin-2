package com.github.chrisjanusa.mvi.package_structure.manager.project.library

data class LibraryGroup(
    val libraryGroupName: String,
    val version: String,
    val libraries: List<Library>,
    val testLibraries: List<Library>,
    val plugins: List<LibraryPlugin>
)