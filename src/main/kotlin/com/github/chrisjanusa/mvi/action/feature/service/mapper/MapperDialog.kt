package com.github.chrisjanusa.mvi.action.feature.service.mapper

import com.github.chrisjanusa.mvi.package_structure.manager.base.ModelFileManager
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.dsl.builder.Cell
import com.intellij.ui.dsl.builder.bindItem
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.builder.selected
import javax.swing.JComponent
import javax.swing.JRadioButton

internal class MapperDialog(
    private val mapperPromptResult: MapperPromptResult,
    private val domainModels: List<ModelFileManager>,
    private val entities: List<ModelFileManager>,
    private val dtos: List<ModelFileManager>
) :
    DialogWrapper(false) {
    init {
        init()
        this.title = "Add a Database to Feature"
    }

    override fun createCenterPanel(): JComponent {
        return panel {
            lateinit var domainModelFrom: Cell<JRadioButton>
            lateinit var entityFrom: Cell<JRadioButton>
            lateinit var dtoFrom: Cell<JRadioButton>
            buttonsGroup("What type of model are you mapping from?") {
                row {
                    domainModelFrom = radioButton("Domain Model")
                }
                row {
                    entityFrom = radioButton("Entity")
                }
                row {
                    dtoFrom = radioButton("DTO")
                }
            }
            row("Domain Model to convert from:") {
                comboBox(items = domainModels)
                    .bindItem(mapperPromptResult::from)
            }.visibleIf(domainModelFrom.selected)
            row("Entity to convert from:") {
                comboBox(items = entities)
                    .bindItem(mapperPromptResult::from)
            }.visibleIf(entityFrom.selected)
            row("Dto to convert from:") {
                comboBox(items = dtos)
                    .bindItem(mapperPromptResult::from)
            }.visibleIf(dtoFrom.selected)
            lateinit var domainModelTo: Cell<JRadioButton>
            lateinit var entityTo: Cell<JRadioButton>
            lateinit var dtoTo: Cell<JRadioButton>
            buttonsGroup("What type of model are you mapping to?") {
                row {
                    domainModelTo = radioButton("Domain Model")
                }
                row {
                    entityTo = radioButton("Entity")
                }
                row {
                    dtoTo = radioButton("DTO")
                }
            }
            row("Domain Model to convert to:") {
                comboBox(items = domainModels)
                    .bindItem(mapperPromptResult::to)
            }.visibleIf(domainModelTo.selected)
            row("Entity to convert to:") {
                comboBox(items = entities)
                    .bindItem(mapperPromptResult::to)
            }.visibleIf(entityTo.selected)
            row("Dto to convert to:") {
                comboBox(items = dtos)
                    .bindItem(mapperPromptResult::to)
            }.visibleIf(dtoTo.selected)
        }
    }
}