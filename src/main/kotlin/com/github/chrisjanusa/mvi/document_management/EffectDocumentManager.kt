package com.github.chrisjanusa.mvi.document_management

import com.github.chrisjanusa.mvi.file_managment.toPascalCase
import com.github.chrisjanusa.mvi.file_managment.getFeaturePackageFile
import com.github.chrisjanusa.mvi.file_managment.getPluginPackageFile
import com.github.chrisjanusa.mvi.file_managment.getRootPackage
import com.github.chrisjanusa.mvi.foundation.addIf
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.editor.Document

class EffectDocumentManager(document: Document, event: AnActionEvent) : DocumentManager(document) {
    private val rootPackage = event.getRootPackage()
    private val featurePackage = event.getFeaturePackageFile()?.name ?: ""
    private val pluginPackage = event.getPluginPackageFile()?.name ?: ""
    private val pluginCapitalized = pluginPackage.toPascalCase()
    private val foundationPath = "$rootPackage.foundation"
    private val generatedPath = "$rootPackage.$featurePackage.plugin.$pluginPackage.generated"

    fun addActionOnlyEffect(effectName: String, actionToFilterFor: String? = null) {
        addImport("$generatedPath.${pluginCapitalized}ActionEffect")
        addImport("$foundationPath.Action")
        addImport("$foundationPath.OnAction")
        addImport("kotlinx.coroutines.flow.Flow")
        if (actionToFilterFor != null) {
            addImport("kotlinx.coroutines.flow.collectLatest")
            addImport("kotlinx.coroutines.flow.filterIsInstance")
        }
        addToBottom("internal object ${effectName}Effect : ${pluginCapitalized}ActionEffect() {\n" +
                "    override suspend fun launchEffect(\n" +
                "        actionFlow: Flow<Action>,\n" +
                "        onAction: OnAction\n" +
                "    ) {\n" +
                "        actionFlow\n".addIf {actionToFilterFor != null} +
                "            .filterIsInstance<${pluginCapitalized}Action.$actionToFilterFor>()\n".addIf {actionToFilterFor != null} +
                "            .collectLatest { action ->\n".addIf {actionToFilterFor != null} +
                "            }\n".addIf {actionToFilterFor != null} +
                "    }\n" +
                "}"
        )
        addImport("$generatedPath.${pluginCapitalized}ActionEffect")
    }

    fun addStateEffect(effectName: String, actionToFilterFor: String? = null) {
        addImport("$generatedPath.${pluginCapitalized}StateEffect")
        addImport("$foundationPath.Action")
        addImport("$foundationPath.OnAction")
        addImport("kotlinx.coroutines.flow.Flow")
        addImport("kotlinx.coroutines.flow.StateFlow")
        if (actionToFilterFor != null) {
            addImport("kotlinx.coroutines.flow.collectLatest")
            addImport("kotlinx.coroutines.flow.filterIsInstance")
        }
        addToBottom("internal object ${effectName}Effect : ${pluginCapitalized}StateEffect() {\n" +
                "\n" +
                "    override suspend fun launchEffect(\n" +
                "        actionFlow: Flow<Action>,\n" +
                "        stateFlow: StateFlow<${pluginCapitalized}State>,\n" +
                "        onAction: OnAction\n" +
                "    ) {\n" +
                "        actionFlow\n".addIf {actionToFilterFor != null} +
                "            .filterIsInstance<${pluginCapitalized}Action.$actionToFilterFor>()\n".addIf {actionToFilterFor != null} +
                "            .collectLatest { action ->\n".addIf {actionToFilterFor != null} +
                "            }\n".addIf {actionToFilterFor != null} +
                "    }\n" +
                "}"
        )
    }

    fun addStateSliceEffect(effectName: String, actionToFilterFor: String? = null) {
        addImport("$generatedPath.${pluginCapitalized}StateSliceEffect")
        addImport("$foundationPath.Action")
        addImport("$foundationPath.OnAction")
        addImport("kotlinx.coroutines.flow.Flow")
        addImport("kotlinx.coroutines.flow.StateFlow")
        if (actionToFilterFor != null) {
            addImport("kotlinx.coroutines.flow.collectLatest")
            addImport("kotlinx.coroutines.flow.filterIsInstance")
        }
        addToBottom("internal object ${effectName}Effect : ${pluginCapitalized}StateSliceEffect() {\n" +
                "\n" +
                "    override suspend fun launchEffect(\n" +
                "        actionFlow: Flow<Action>,\n" +
                "        stateFlow: StateFlow<${pluginCapitalized}State>,\n" +
                "        sliceFlow: StateFlow<${pluginCapitalized}Slice>,\n" +
                "        onAction: OnAction\n" +
                "    ) {\n" +
                "        actionFlow\n".addIf {actionToFilterFor != null} +
                "            .filterIsInstance<${pluginCapitalized}Action.$actionToFilterFor>()\n".addIf {actionToFilterFor != null} +
                "            .collectLatest { action ->\n".addIf {actionToFilterFor != null} +
                "            }\n".addIf {actionToFilterFor != null} +
                "    }\n" +
                "}"
        )
    }

    fun addNavEffect(effectName: String, actionToFilterFor: String? = null, navAction: String? = null, isCoreAction: Boolean = false) {
        if (isCoreAction) {
            addImport("$rootPackage.common.nav.CoreNavAction")
        }
        addImport("$generatedPath.${pluginCapitalized}NavEffect")
        addImport("$foundationPath.Action")
        addImport("$foundationPath.OnAction")
        addImport("$foundationPath.OnAppAction")
        addImport("kotlinx.coroutines.flow.Flow")
        if (actionToFilterFor != null) {
            addImport("kotlinx.coroutines.flow.collectLatest")
            addImport("kotlinx.coroutines.flow.filterIsInstance")
        }
        val appAction = if (isCoreAction) "CoreNavAction.$navAction" else "${pluginCapitalized}NavAction.$navAction"
        val onAppAction = "onAppAction($appAction)"
        addToBottom("internal object ${effectName}Effect : ${pluginCapitalized}NavEffect() {\n" +
                "    override suspend fun launchEffect(\n" +
                "        actionFlow: Flow<Action>,\n" +
                "        onAppAction: OnAppAction,\n" +
                "        onAction: OnAction\n" +
                "    ) {\n" +
                "        actionFlow\n".addIf {actionToFilterFor != null} +
                "            .filterIsInstance<${pluginCapitalized}Action.$actionToFilterFor>()\n".addIf {actionToFilterFor != null} +
                "            .collectLatest { action ->\n".addIf {actionToFilterFor != null} +
                "                $onAppAction\n".addIf { actionToFilterFor != null && navAction !=null } +
                "            }\n".addIf {actionToFilterFor != null} +
                "        $onAppAction\n".addIf { actionToFilterFor == null && navAction !=null } +
                "    }\n" +
                "}"
        )
    }
}