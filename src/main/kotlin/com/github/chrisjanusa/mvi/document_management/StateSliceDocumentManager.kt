package com.github.chrisjanusa.mvi.document_management

import com.github.chrisjanusa.mvi.file_managment.toPascalCase
import com.github.chrisjanusa.mvi.file_managment.getFeaturePackageFile
import com.github.chrisjanusa.mvi.file_managment.getPluginPackageFile
import com.github.chrisjanusa.mvi.file_managment.getRootPackage
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.editor.Document

class StateSliceDocumentManager(document: Document, event: AnActionEvent) : DocumentManager(document) {
    private val rootPackage = event.getRootPackage()
    private val featurePackage = event.getFeaturePackageFile()?.name ?: ""
    private val pluginPackage = event.getPluginPackageFile()?.name ?: ""
    private val sliceName = "${pluginPackage.toPascalCase()}Slice"
    private val noSliceName = "NoSlice"
    private val slicePackage = "$rootPackage.$featurePackage.plugin.$pluginPackage.$sliceName"
    private val noSlicePackage = "$rootPackage.foundation.state.NoSlice"
    private val stateName = "${pluginPackage.toPascalCase()}State"
    private val noStateName = "NoState"
    private val statePackage = "$rootPackage.$featurePackage.plugin.$pluginPackage.$stateName"
    private val noStatePackage = "$rootPackage.foundation.state.$noStateName"

    fun addSlice() {
        findAndReplace(noSlicePackage, slicePackage)
        findAndReplace(noSliceName, sliceName)
    }

    fun removeSlice() {
        findAndReplace(slicePackage, noSlicePackage)
        findAndReplace(sliceName, noSliceName)
    }

    fun addSlicePackage() {
        addImport(slicePackage)
    }

    fun removeSlicePackage() {
        removeImport(slicePackage)
    }

    fun addState() {
        findAndReplace(noStatePackage, statePackage)
        findAndReplace(noStateName, sliceName)
    }

    fun removeState() {
        findAndReplace(statePackage, noStatePackage)
        findAndReplace(sliceName, noStateName)
    }
}