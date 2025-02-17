package com.github.chrisjanusa.mvi

import com.github.chrisjanusa.mvi.app.CreateAppAction
import com.github.chrisjanusa.mvi.feature.CreateFeatureAction
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup


class MviGroup : DefaultActionGroup() {
    override fun update(event: AnActionEvent) {
        event.presentation.isEnabledAndVisible = CreateFeatureAction.isEnabled(event) || CreateAppAction.isEnabled(event)
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }
}
