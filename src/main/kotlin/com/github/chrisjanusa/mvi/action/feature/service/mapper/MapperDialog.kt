package com.github.chrisjanusa.mvi.action.feature.service.mapper

import com.github.chrisjanusa.mvi.package_structure.manager.base.ModelFileManager
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.dsl.builder.Cell
import com.intellij.ui.dsl.builder.bindItem
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComboBox
import javax.swing.JComponent

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
            lateinit var fromComboBox: Cell<JComboBox<ModelFileManager>>
            buttonsGroup("What type of model are you mapping from?") {
                row {
                    radioButton("Domain Model")
                        .component
                        .addActionListener {
                            fromComboBox.component.removeAllItems()
                            domainModels.forEach {
                                fromComboBox.component.addItem(it)
                            }
                        }
                }
                row {
                    radioButton("Entity")
                        .component
                        .addActionListener {
                            fromComboBox.component.removeAllItems()
                            entities.forEach {
                                fromComboBox.component.addItem(it)
                            }
                        }
                }
                row {
                    radioButton("DTO")
                        .component
                        .addActionListener {
                            fromComboBox.component.removeAllItems()
                            dtos.forEach {
                                fromComboBox.component.addItem(it)
                            }
                        }
                }
            }
            row("Model to convert from:") {
                fromComboBox = comboBox(items = domainModels)
                    .bindItem(mapperPromptResult::from)
            }
            lateinit var toComboBox: Cell<JComboBox<ModelFileManager>>
            buttonsGroup("What type of model are you mapping to?") {
                row {
                    radioButton("Domain Model")
                        .component
                        .addActionListener {
                            toComboBox.component.removeAllItems()
                            domainModels.forEach {
                                toComboBox.component.addItem(it)
                            }
                        }
                }
                row {
                    radioButton("Entity")
                        .component
                        .addActionListener {
                            toComboBox.component.removeAllItems()
                            entities.forEach {
                                toComboBox.component.addItem(it)
                            }
                        }
                }
                row {
                    radioButton("DTO")
                        .component
                        .addActionListener {
                            toComboBox.component.removeAllItems()
                            dtos.forEach {
                                toComboBox.component.addItem(it)
                            }
                        }
                }
            }
            row("Model to convert to:") {
                toComboBox = comboBox(items = domainModels)
                    .bindItem(mapperPromptResult::to)
            }
        }
    }
}