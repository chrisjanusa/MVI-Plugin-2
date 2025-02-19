package com.github.chrisjanusa.mvi.document_management

import com.github.chrisjanusa.mvi.file_managment.capitalize
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
    private val pluginCapitalized = pluginPackage.capitalize()
    private val typeAliasPath = "$rootPackage.$featurePackage.plugin.$pluginPackage.generated"

    fun addActionOnlyEffect(effectName: String, actionToFilterFor: String? = null) {
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
        addImport("$typeAliasPath.${pluginCapitalized}ActionEffect")
    }

    fun addStateEffect(effectName: String, actionToFilterFor: String? = null) {
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
        addImport("$typeAliasPath.${pluginCapitalized}StateEffect")
    }

    fun addStateSliceEffect(effectName: String, actionToFilterFor: String? = null) {
        addToBottom("internal object ${effectName}Effect : ${pluginCapitalized}StateSliceEffect() {\n" +
                "\n" +
                "    override suspend fun launchEffect(\n" +
                "        actionFlow: Flow<Action>, \n" +
                "        stateFlow: StateFlow<${pluginCapitalized}State>, \n" +
                "        sliceFlow: StateFlow<${pluginCapitalized}Slice>, \n" +
                "        onAction: OnAction\n" +
                "    ) {\n" +
                "        actionFlow\n".addIf {actionToFilterFor != null} +
                "            .filterIsInstance<${pluginCapitalized}Action.$actionToFilterFor>()\n".addIf {actionToFilterFor != null} +
                "            .collectLatest { action ->\n".addIf {actionToFilterFor != null} +
                "            }\n".addIf {actionToFilterFor != null} +
                "    }\n" +
                "}"
        )
        addImport("$typeAliasPath.${pluginCapitalized}StateSliceEffect")
    }

    fun addNavEffect(effectName: String, actionToFilterFor: String? = null, navAction: String? = null, isCoreAction: Boolean = false) {
        addToBottom("internal object ${effectName}Effect : ${pluginCapitalized}NavEffect() {\n" +
                "    override suspend fun launchEffect(\n" +
                "        actionFlow: Flow<Action>, \n" +
                "        onAppAction: OnAppAction, \n" +
                "        onAction: OnAction\n" +
                "    ) {\n" +
                "        actionFlow\n".addIf {actionToFilterFor != null} +
                "            .filterIsInstance<${pluginCapitalized}Action.$actionToFilterFor>()\n".addIf {actionToFilterFor != null} +
                "            .collectLatest { action ->\n".addIf {actionToFilterFor != null} +
                "                onAppAction(${pluginCapitalized}NavAction.$navAction)\n"
                    .addIf {
                        actionToFilterFor != null && navAction !=null && !isCoreAction
                    } +
                "                onAppAction(CoreNavAction.$navAction)\n"
                    .addIf {
                        actionToFilterFor != null && navAction !=null && isCoreAction
                    } +
                "            }\n" +
                "    }\n" +
                "}"
        )
        if (isCoreAction) {
            addImport("$rootPackage.common.nav.CoreNavAction")
        }
        addImport("$typeAliasPath.${pluginCapitalized}NavEffect")
    }
}