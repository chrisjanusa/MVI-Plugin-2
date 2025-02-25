package com.github.chrisjanusa.mvi.action_group

import com.github.chrisjanusa.mvi.action.feature.plugin.action.AddActionAction
import com.github.chrisjanusa.mvi.action.feature.plugin.effect.AddEffectAction
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup


class MviEditorGroup : DefaultActionGroup() {
    override fun update(event: AnActionEvent) {
        event.presentation.isEnabledAndVisible = AddEffectAction.isEnabled(event) ||
                AddActionAction.isEnabled(event)
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }
}
