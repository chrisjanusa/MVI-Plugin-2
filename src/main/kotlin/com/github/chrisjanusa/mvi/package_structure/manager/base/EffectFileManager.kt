package com.github.chrisjanusa.mvi.package_structure.manager.base

import com.intellij.openapi.vfs.VirtualFile

abstract class EffectFileManager(file: VirtualFile) : FileManager(file) {
    internal abstract val rootPath: String
    internal abstract val baseName: String
    internal abstract val state: String
    internal abstract val slice: String
    internal abstract val actionName: String
    internal abstract val typeAliasPath: String
    abstract val fileName: String
    abstract val actionEffect: String
    abstract val stateEffect: String
    abstract val stateSliceEffect: String
    abstract val navEffect: String

    private val foundationPath by lazy {
        "$rootPath.foundation"
    }

    fun addActionOnlyEffect(effectName: String, actionToFilterFor: String? = null) {
        addImport("$typeAliasPath.$actionEffect")
        addImport("$foundationPath.Action")
        addImport("$foundationPath.OnAction")
        addImport("kotlinx.coroutines.flow.Flow")
        if (actionToFilterFor != null) {
            addImport("kotlinx.coroutines.flow.collectLatest")
            addImport("kotlinx.coroutines.flow.filterIsInstance")
        }
        addToBottom("internal object ${getEffectFullName(effectName)} : $actionEffect() {\n" +
                "    override suspend fun launchEffect(\n" +
                "        actionFlow: Flow<Action>,\n" +
                "        onAction: OnAction\n" +
                "    ) {\n" +
                "        actionFlow\n".addIf {actionToFilterFor != null} +
                "            .filterIsInstance<$actionName.$actionToFilterFor>()\n".addIf {actionToFilterFor != null} +
                "            .collectLatest { action ->\n".addIf {actionToFilterFor != null} +
                "            }\n".addIf {actionToFilterFor != null} +
                "    }\n" +
                "}"
        )
        addEffectToViewModel(effectName)
        writeToDisk()
    }

    fun addStateEffect(effectName: String, actionToFilterFor: String? = null) {
        addImport("$typeAliasPath.$stateEffect")
        addImport("$foundationPath.Action")
        addImport("$foundationPath.OnAction")
        addImport("kotlinx.coroutines.flow.Flow")
        addImport("kotlinx.coroutines.flow.StateFlow")
        if (actionToFilterFor != null) {
            addImport("kotlinx.coroutines.flow.collectLatest")
            addImport("kotlinx.coroutines.flow.filterIsInstance")
        }
        addToBottom("internal object ${getEffectFullName(effectName)} : $stateEffect() {\n" +
                "\n" +
                "    override suspend fun launchEffect(\n" +
                "        actionFlow: Flow<Action>,\n" +
                "        stateFlow: StateFlow<$state>,\n" +
                "        onAction: OnAction\n" +
                "    ) {\n" +
                "        actionFlow\n".addIf {actionToFilterFor != null} +
                "            .filterIsInstance<$actionName.$actionToFilterFor>()\n".addIf {actionToFilterFor != null} +
                "            .collectLatest { action ->\n".addIf {actionToFilterFor != null} +
                "            }\n".addIf {actionToFilterFor != null} +
                "    }\n" +
                "}"
        )
        addEffectToViewModel(effectName)
        writeToDisk()
    }

    fun addStateSliceEffect(effectName: String, actionToFilterFor: String? = null) {
        addImport("$typeAliasPath.$stateSliceEffect")
        addImport("$foundationPath.Action")
        addImport("$foundationPath.OnAction")
        addImport("kotlinx.coroutines.flow.Flow")
        addImport("kotlinx.coroutines.flow.StateFlow")
        if (actionToFilterFor != null) {
            addImport("kotlinx.coroutines.flow.collectLatest")
            addImport("kotlinx.coroutines.flow.filterIsInstance")
        }
        addToBottom("internal object ${getEffectFullName(effectName)} : $stateSliceEffect() {\n" +
                "\n" +
                "    override suspend fun launchEffect(\n" +
                "        actionFlow: Flow<Action>,\n" +
                "        stateFlow: StateFlow<$state>,\n" +
                "        sliceFlow: StateFlow<$slice>,\n" +
                "        onAction: OnAction\n" +
                "    ) {\n" +
                "        actionFlow\n".addIf {actionToFilterFor != null} +
                "            .filterIsInstance<$actionName.$actionToFilterFor>()\n".addIf {actionToFilterFor != null} +
                "            .collectLatest { action ->\n".addIf {actionToFilterFor != null} +
                "            }\n".addIf {actionToFilterFor != null} +
                "    }\n" +
                "}"
        )
        addEffectToViewModel(effectName)
        writeToDisk()
    }

    fun addNavEffect(effectName: String, actionToFilterFor: String? = null, navAction: String? = null, isCoreAction: Boolean = false) {
        if (isCoreAction) {
            addImport("$rootPath.common.nav.CoreNavAction")
        }
        addImport("$typeAliasPath.$navEffect")
        addImport("$foundationPath.Action")
        addImport("$foundationPath.OnAction")
        addImport("$foundationPath.OnAppAction")
        addImport("kotlinx.coroutines.flow.Flow")
        if (actionToFilterFor != null) {
            addImport("kotlinx.coroutines.flow.collectLatest")
            addImport("kotlinx.coroutines.flow.filterIsInstance")
        }
        val appAction = if (isCoreAction) "CoreNavAction.$navAction" else "${baseName}${ActionFileManager.NAV_SUFFIX}.$navAction"
        val onAppAction = "onAppAction($appAction)"
        addToBottom("internal object ${getEffectFullName(effectName)} : $navEffect() {\n" +
                "    override suspend fun launchEffect(\n" +
                "        actionFlow: Flow<Action>,\n" +
                "        onAppAction: OnAppAction,\n" +
                "        onAction: OnAction\n" +
                "    ) {\n" +
                "        actionFlow\n".addIf {actionToFilterFor != null} +
                "            .filterIsInstance<$actionName.$actionToFilterFor>()\n".addIf {actionToFilterFor != null} +
                "            .collectLatest { action ->\n".addIf {actionToFilterFor != null} +
                "                $onAppAction\n".addIf { actionToFilterFor != null && navAction !=null } +
                "            }\n".addIf {actionToFilterFor != null} +
                "        $onAppAction\n".addIf { actionToFilterFor == null && navAction !=null } +
                "    }\n" +
                "}"
        )
        addEffectToViewModel(effectName)
        writeToDisk()
    }

    abstract fun getEffectFullName(effectName: String) : String

    abstract fun addEffectToViewModel(effectName: String)

    companion object {
        const val SUFFIX = "Effect"
        const val ACTION_SUFFIX = "ActionEffect"
        const val STATE_SUFFIX = "StateEffect"
        const val STATE_SLICE_SUFFIX = "StateSliceEffect"
        const val NAV_SUFFIX = "NavEffect"
    }
}