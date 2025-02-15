package com.github.chrisjanusa.kmpmvi

import com.github.chrisjanusa.kmpmvi.feature.CreateFeatureAction
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup


class KmpMviGroup : DefaultActionGroup() {
    override fun update(event: AnActionEvent) {
        event.presentation.isEnabledAndVisible = CreateFeatureAction.isEnabled(event)
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }
}
