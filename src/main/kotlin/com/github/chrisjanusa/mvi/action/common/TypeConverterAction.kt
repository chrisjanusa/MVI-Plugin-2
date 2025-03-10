package com.github.chrisjanusa.mvi.action.common


import com.github.chrisjanusa.mvi.package_structure.getManager
import com.github.chrisjanusa.mvi.package_structure.getManagerOfType
import com.github.chrisjanusa.mvi.package_structure.manager.RootPackage
import com.github.chrisjanusa.mvi.package_structure.manager.common.CommonPackage
import com.github.chrisjanusa.mvi.package_structure.manager.feature.service.FeatureServicePackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.CommonChild
import com.github.chrisjanusa.mvi.package_structure.parent_provider.ServiceChild
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class TypeConverterAction : AnAction("Create _Type Converter") {
    override fun actionPerformed(event: AnActionEvent) {
        val rootPackage = event.getManagerOfType(RootPackage) as? RootPackage ?: return
        val commonPackage = rootPackage.commonPackage ?: return
        val typeConverterPromptResult = TypeConverterPromptResult()
        val dialog = TypeConverterDialog(typeConverterPromptResult)
        val isCancelled = !dialog.showAndGet()
        if (isCancelled) return
        commonPackage.createTypeConverter(
            type = typeConverterPromptResult.type,
            typeConverterName = typeConverterPromptResult.name,
        )
    }

    override fun update(event: AnActionEvent) {
        event.presentation.isEnabledAndVisible = isEnabled(event)
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

    companion object {
        fun isEnabled(event: AnActionEvent): Boolean {
            val manager = event.getManager() ?: return false
            if (manager is CommonChild || manager is CommonPackage) return true
            if (manager is ServiceChild || manager is FeatureServicePackage) return true
            return false
        }
    }
}