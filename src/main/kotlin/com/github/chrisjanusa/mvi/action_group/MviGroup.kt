package com.github.chrisjanusa.mvi.action_group

import com.github.chrisjanusa.mvi.action.app.CreateAppAction
import com.github.chrisjanusa.mvi.action.common.TypeConverterAction
import com.github.chrisjanusa.mvi.action.feature.CreateFeatureAction
import com.github.chrisjanusa.mvi.action.feature.domain_model.CreateDomainModelAction
import com.github.chrisjanusa.mvi.action.feature.nav.CreateNavGraphAction
import com.github.chrisjanusa.mvi.action.feature.plugin.PluginAction
import com.github.chrisjanusa.mvi.action.feature.plugin.action.AddActionAction
import com.github.chrisjanusa.mvi.action.feature.plugin.effect.AddEffectAction
import com.github.chrisjanusa.mvi.action.feature.plugin.slice.AddSliceAction
import com.github.chrisjanusa.mvi.action.feature.plugin.slice.RemoveSliceAction
import com.github.chrisjanusa.mvi.action.feature.plugin.state.AddStateAction
import com.github.chrisjanusa.mvi.action.feature.plugin.state.RemoveStateAction
import com.github.chrisjanusa.mvi.action.feature.service.database.DatabaseAction
import com.github.chrisjanusa.mvi.action.feature.service.database.EntityAction
import com.github.chrisjanusa.mvi.action.feature.service.mapper.MapperAction
import com.github.chrisjanusa.mvi.action.feature.service.remote.RemoteDataSourceAction
import com.github.chrisjanusa.mvi.action.feature.service.repository.RepositoryAction
import com.github.chrisjanusa.mvi.action.feature.shared.AddSharedActionAction
import com.github.chrisjanusa.mvi.action.feature.shared.AddSharedEffectAction
import com.github.chrisjanusa.mvi.action.feature.shared.AddSliceUpdateEffectAction
import com.github.chrisjanusa.mvi.action.feature.shared.CreateSharedStateAction
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
        fun isEnabledPlugin(event: AnActionEvent): Boolean =
            AddSliceAction.isEnabled(event) ||
            AddStateAction.isEnabled(event) ||
            PluginAction.isEnabled(event) ||
            RemoveSliceAction.isEnabled(event) ||
            RemoveStateAction.isEnabled(event) ||
            AddEffectAction.isEnabled(event) ||
            AddActionAction.isEnabled(event)

        fun isEnabledFeature(event: AnActionEvent): Boolean =
            CreateFeatureAction.isEnabled(event) ||
            CreateDomainModelAction.isEnabled(event) ||
            CreateNavGraphAction.isEnabled(event) ||
            CreateSharedStateAction.isEnabled(event) ||
            AddSharedActionAction.isEnabled(event) ||
            AddSharedEffectAction.isEnabled(event) ||
            AddSliceUpdateEffectAction.isEnabled(event) ||
            RepositoryAction.isEnabled(event) ||
            DatabaseAction.isEnabled(event) ||
            EntityAction.isEnabled(event) ||
            MapperAction.isEnabled(event) ||
            RemoteDataSourceAction.isEnabled(event) ||
            TypeConverterAction.isEnabled(event)

        fun isEnabledApp(event: AnActionEvent): Boolean = CreateAppAction.isEnabled(event)
    }
}
