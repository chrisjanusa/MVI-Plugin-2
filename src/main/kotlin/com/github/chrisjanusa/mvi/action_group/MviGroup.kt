package com.github.chrisjanusa.mvi.action_group

import com.github.chrisjanusa.mvi.package_generator.app.CreateAppAction
import com.github.chrisjanusa.mvi.package_generator.feature.CreateFeatureAction
import com.github.chrisjanusa.mvi.package_generator.feature.domain_model.CreateDomainModelAction
import com.github.chrisjanusa.mvi.package_generator.feature.nav.CreateNavGraphAction
import com.github.chrisjanusa.mvi.package_generator.feature.repository.RepositoryAction
import com.github.chrisjanusa.mvi.package_generator.feature.shared.CreateSharedStateAction
import com.github.chrisjanusa.mvi.package_generator.plugin.PluginAction
import com.github.chrisjanusa.mvi.package_generator.plugin.action.AddActionAction
import com.github.chrisjanusa.mvi.package_generator.plugin.effect.AddEffectAction
import com.github.chrisjanusa.mvi.package_generator.plugin.slice.AddSliceAction
import com.github.chrisjanusa.mvi.package_generator.plugin.slice.RemoveSliceAction
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup


class MviGroup : DefaultActionGroup() {
    override fun update(event: AnActionEvent) {
        event.presentation.isEnabledAndVisible = isEnabledApp(event) ||
                isEnabledFeature(event) ||
                isEnabledPlugin(event)
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

    companion object {
        fun isEnabledPlugin(event: AnActionEvent): Boolean = AddSliceAction.isEnabled(event) ||
                PluginAction.isEnabled(event) ||
                RemoveSliceAction.isEnabled(event) ||
                AddEffectAction.isEnabled(event) ||
                AddActionAction.isEnabled(event)

        fun isEnabledFeature(event: AnActionEvent): Boolean = CreateFeatureAction.isEnabled(event) ||
                CreateDomainModelAction.isEnabled(event) ||
                CreateNavGraphAction.isEnabled(event) ||
                CreateSharedStateAction.isEnabled(event) ||
                RepositoryAction.isEnabled(event)

        fun isEnabledApp(event: AnActionEvent): Boolean = CreateAppAction.isEnabled(event)
    }
}
